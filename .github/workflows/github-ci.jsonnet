local configuration = import 'configuration.jsonnet';

local filter_change_parent_pom() = { "parent": [ "pom.xml" ] };

local rule_change_in_directory(directory) = { changes+: [directory + '/**/*'] };
local rule_manual() = { when: 'manual' };
local command_docker_login_local = 'echo $DOCKER_REPO_PASSWORD | docker login $DOCKER_REPO_URL_LOGIN -u $DOCKER_REPO_USERNAME --password-stdin';

local filterArray() = {
        [container.name]:  [
            './' + container.name + '/**'
        ] for container in configuration.containers
    };


local filters() = std.manifestYamlDoc(filterArray() + filter_change_parent_pom(), false);

local job_changes() = {
     changes: {
        "runs-on": [ "self-hosted" ],
        //container: configuration.dockerImage,
        # Required permissions
        permissions:{
            "pull-requests": "read"
        },
        outputs: { parent: "${{ steps.filter.outputs.parent }}" }
         + {
            [container.name]: "${{ steps.filter.outputs." + container.name + " }}"
            for container in configuration.containers
         },
        steps: [
            {
                uses: "dorny/paths-filter@v3",
                id: "filter",
                with: { filters: filters() },
            },
        ],
     }
};

local job_build_parent() = {
  'build-parent': {
    "runs-on": [ "self-hosted" ],
    container: configuration.dockerImage,
    needs: "changes",
    "if": "${{ github.event.inputs.build == 'parent' || needs.changes.outputs.parent == 'true' && always() }}",
    steps: [
        {
            uses: "actions/checkout@v3",
            with: {
              ref: "${{ github.event.workflow_run.head_branch }}"
            },
        },
        { run: 'mvn --non-recursive clean package' },
    ],
  },
};

local job_build_service(container_name) = {
  local projectName = 'dripid/',
  local image = '[[ -n "${GITHUB_REF_NAME}" ]]' +
   ' && echo IMAGE=' + projectName + container_name + ':${GITHUB_REF_NAME}' +
   ' || echo IMAGE=' + projectName + container_name + ':${GITHUB_HEAD_REF}-${GITHUB_BASE_REF}',
  //local image = if std.length(var_tag) != 0
    //then projectName + container_name + ':' + var_tag
    //else projectName + container_name + ':${GITHUB_HEAD_REF}-${GITHUB_BASE_REF}',
  //local image = projectName + container_name + ':' + var_tag,
  local var_image = '$DOCKER_REPO_URL$CI_PROJECT_NAME/' + container_name,

//  variables: {
//    GIT_SUBMODULE_STRATEGY: 'recursive',
//  },
  "runs-on": [ "self-hosted" ],
  needs: [ "changes", "build-parent" ],
  "if": "${{ github.event.inputs.build == '" + container_name + "' || needs.changes.outputs." + container_name + " == 'true' && always() }}",
  steps: [
    { uses: "actions/checkout@v3", },
    { run: command_docker_login_local },
    { run: image },
    { run: 'mvn clean spring-boot:build-image' +
          ' -Dspring-boot.build-image.imageName=$IMAGE' +
          ' -Dbuilder=$DOCKER_REPO_URLdocker/builder-jammy-base-0_4_278:latest' +
          ' -DrunImage=$DOCKER_REPO_URLdocker/run-jammy-base-0_1_105:latest' +
          ' -Ddocker.repo.url=$CI_REGISTRY' +
          ' -Ddocker.repo.username=$DOCKER_REPO_USERNAME' +
          ' -Ddocker.repo.password=$DOCKER_REPO_PASSWORD'
          },
    { run: 'docker tag ' + projectName + container_name + ':${GITHUB_REF_NAME}' + ' ' + var_image + ':${GITHUB_REF_NAME}' },
    { run: 'docker push ' + var_image + ':${GITHUB_REF_NAME}' },
  ],
};

local build_services() = {
  ['build-' + container.name]:
    job_build_service(container.name)
  for container in configuration.containers
};
//
//local generate_compose_all() = [
//  [
//      'cat docker-compose.yaml-links.yml docker-compose.' + deploymentGroup.name + '.yml > docker-compose.' + deploymentGroup.name + '.gen.yml',
//      'env $(cat ./env/$CI_COMMIT_BRANCH/.env | grep ^[A-Z] | xargs) ' +
//      'docker --context $CI_COMMIT_BRANCH ' +
//      'stack deploy -c docker-compose.' + deploymentGroup.name + '.gen.yml ' +
//      '--with-registry-auth ucso-' + deploymentGroup.name,
//  ]
//  for deploymentGroup in configuration.deploymentGroups
//];
//
//local deploy_local_server() = if ( std.extVar('branch') == 'test' || std.extVar('branch') == 'develop' || std.extVar('branch') == 'develop-old' || std.extVar('branch') == 'test-old' ) then {
//  ['update-' + std.extVar('branch') + '-server']: {
//    stage: 'local-deploy',
//    cache: { },
//    script:
//    [
//      'cd docker',
//      command_docker_login_local,
//      'echo $DOCKER_REPO_URL',
//    ] +
//    generate_compose_all(),
//    rules: [
//      rule_merge_request() + { when: 'never' },
//      rule_on_success(),
//      rule_manual() + rule_allow_failure(),
//    ],
//    tags: ['ucso-deploy-manager'],
//  }
//} else { };


local jsonPipeline =
{
  name: "Create and publish a Service image",
  on: {
      workflow_dispatch: {
          branches: [
                "develop",
                "test-microservices"
            ],
          inputs: {
              build: {
                  type: "choice",
                  description: "Who to build",
                  options: [
                     "parent"
                  ] + [
                      container.name for container in configuration.containers
                  ],
              },
          }
      },
      workflow_run: {
          workflows: [ "Create all jobs" ],
          types: [ "completed" ],
          branches: [
              "develop",
              "test-microservices"
          ],
      },
      push: {
          "paths-ignore": [ '.github/**' ]
      }
  },
    env: {
          DOCKER_REPO_PASSWORD: "${{ secrets.DOCKER_REPO_PASSWORD }}",
          DOCKER_REPO_USERNAME: "${{ secrets.DOCKER_REPO_USERNAME }}",
          DOCKER_REPO_URL_LOGIN: "${{ vars.DOCKER_REPO_URL_LOGIN }}",
          DOCKER_REPO_URL: "${{ vars.DOCKER_REPO_URL }}",
          CI_PROJECT_NAME: "${{ github.event.repository.name }}",
          CI_REGISTRY: "ghcr.io"
      },
} + {
    jobs: job_changes()
    + job_build_parent()
    + build_services()
};
//+ deploy_local_server()

std.manifestYamlDoc( jsonPipeline, true)