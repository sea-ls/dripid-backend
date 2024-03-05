local configuration = import 'configuration.jsonnet';

local filter_change_parent_pom() = { filters+: 'parent:\n- pom.xml\n' };

local rule_change_in_directory(directory) = { changes+: [directory + '/**/*'] };
local rule_manual() = { when: 'manual' };
local rule_on_success() = { when: 'on_success' };
local rule_allow_failure() = { allow_failure: true };
local command_docker_login_local = 'echo -n $GITLAB_CI_PASSWORD | docker login $CI_REGISTRY --username $GITLAB_CI_USERNAME --password-stdin';
local command_docker_login_remote = 'echo -n $UCSO_REMOTE_REGISTRY_PASS | docker login $UCSO_REMOTE_REGISTRY --username $UCSO_REMOTE_REGISTRY_USER --password-stdin';


local filterArray = [ "pom.xml" ];

local filters = std.manifestYamlDoc(filterArray, false);

local job_changes() = {
     changes: {
        "runs-on": [ "self-hosted" ],
        container: configuration.dockerImage,
        # Required permissions
        permissions:{
            "pull-requests": "read"
        },
        outputs: { parent: "${{ steps.filter.outputs.parent }}" },
        steps: [
            {
                uses: "dorny/paths-filter@v3",
                id: "filter",
                with: filter_change_parent_pom(),
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
        { uses: "actions/checkout@v3" },
        { run: 'mvn --non-recursive clean package' },
    ],
  },
};

//local job_build_service(container_name, dependsOnLibs, dependSubmodule) = {
//  local var_tag = '$CI_COMMIT_BRANCH',
//  local image = if std.length(std.extVar('branch')) != 0
//    then 'ucso/' + container_name + ':' + var_tag
//    else 'ucso/' + container_name + ':$CI_MERGE_REQUEST_SOURCE_BRANCH_NAME-$CI_MERGE_REQUEST_TARGET_BRANCH_NAME',
//  local var_image = '${DOCKER_REPO_URL}${CI_PROJECT_NAME}/' + container_name,
//
//  variables: {
//    GIT_SUBMODULE_STRATEGY: 'recursive',
//  },
//  stage: 'build-service',
//  script: [
//    'echo ' + mvn_repo,
//    command_docker_login_local,
//    'mvn clean spring-boot:build-image' +
//          ' -Dspring-boot.build-image.imageName=' + image + ' -pl ' + container_name + mvn_repo +
//          ' -Dbuilder=${DOCKER_REPO_URL}ucso-docker/paketo-full:latest' +
//          ' -DrunImage=${DOCKER_REPO_URL}ucso-docker/paketo-run-full:latest' +
//          ' -Ddocker.repo.url=${CI_REGISTRY}' +
//          ' -Ddocker.repo.username=${GITLAB_CI_USERNAME}' +
//          ' -Ddocker.repo.password=${GITLAB_CI_PASSWORD}',
//          //+ ' -DrunImage=paketobuildpacks/run:1.2.60-base-cnb',// + ' -DbuildImage=paketobuildpacks/run:1.2.60-base-cnb',
//          //' -DrunImage=${DOCKER_REPO_URL}ucso-docker/paketo-run-base:latest',
//    'docker tag ucso/' + container_name + ':' + var_tag + ' ' + var_image + ':' + var_tag,
//    'docker push ' + var_image + ':' + var_tag,
//    'BUILT_CONTAINER_NAME_' + std.strReplace(container_name, '-', '_') + '=' + container_name
//  ],
//  retry: 1,
//  rules: [rule_merge_request()] + rule_openapi_changes(container_name) + [
//    filter_change_parent_pom() + rule_change_in_directory(container_name) + rule_change_libs(dependsOnLibs),
//    rule_manual() + rule_allow_failure(),
//  ],
//  tags: ['docker-builder'],
//};
//
//local build_services() = {
//  ['build-' + container.container_name]:
//    job_build_service(container.container_name, container.dependOnLibs, container.dependSubmodule)
//  for container in configuration.containers
//};
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
//local collect_compose_all() = [
//  '-c docker-compose.' + deploymentGroup.name + '.gen.yml'
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
} + {
    jobs: job_changes()
    + job_build_parent()
};

//+ build_services()
//+ deploy_local_server()

std.manifestYamlDoc( jsonPipeline, true)
