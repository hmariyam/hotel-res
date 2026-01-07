# This file explains how to add a repo from your github account to be synced with gitlab project

Basically, you will juste have to create an access token to your github repo __with correct rights__, add it to gitlab and provide your github repo link to the gitlab pipeline



## Create an access token on github
1. Create your repo on github. it could be public or private, depends on you. you could also change it later without any problem
2. Go to `Github settings > Developer settings > Personal access token > Fine grained tokens > "Generate new token"`
3. Fill out the form
    - add a token name (whatever the name is), 
    - select only selected repos
    - grant access to only one repo ( the related one),
    - add permissions: here is what are mandatory
   
        | rights          | access         |
        |-----------------|----------------|
        | Issues          | read and write |
        | Commit statuses | read and write |
        | Administration  | read and write |
        | Contents        | read and write |
        | Merge queues    | read and write |
        | Pull requests   | read and write |
        | Environements   | read and write |
        | Issues          | read and write |
   **NB:** *All those access rights are not used right now there could be some modifications in the pipeline and if the token doesn't the required rights, the pipeline will fail! yep. but it is what it is hahaha*
4. copy the token

## Add the token to gitlab:
1. in gitlab, Go to `Settings > CI/CD > Variables > "Add variable"`, 
2. Name the variable like `BOB_GH_TOKEN` for a user named Bob,

## Add your repo 'endpoint' and variable name in .cd-remotes and push
This step will be in your ide
- go to `/docs/pipelineConfigs/.cd-remotes`
- Add a new line using the pattern `yourGithuUsername/yourGithubRepoName.git`: `YOUR_GITLAB_TOKEN_NAME`
- __Always keep the last line empty__
- push it to any branch you want. 
- check if the pipeline logo appear & is running.