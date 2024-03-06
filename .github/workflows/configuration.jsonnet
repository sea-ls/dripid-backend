{

  //variables: {
  //  MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository",
 // },
  dockerImage: '${{ vars.DOCKER_REPO_URL }}docker/docker-25',

  deploymentGroups: [
    {name: 'config',             containers: ['config']},
    {name: 'auth',               containers: ['auth-service']},
    {name: 'gateway',            containers: ['gateway-service']},
    {name: 'delivery',           containers: ['delivery-service']},
  ],
}