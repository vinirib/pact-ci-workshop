def consumerUrl = 'https://github.com/vinirib/pact-consumer-sample'
def providerUrl = 'https://github.com/vinirib/pact-provider-sample'
def consumerAppName = 'client-api'
def providerAppName = 'account-api'
def branch = 'feature-docker-pipeline'

pipelineJob("1-PACT-FLOW-CONSUMER-GENERATE-AND-PUBLISH-CONTRACT") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(consumerUrl)
                    }
                    branch(branch)
                    extensions {}
                }
            }
             scriptPath("Jenkinsfile-generate-and-publish-contract")
        }
    }
}


pipelineJob("2-PACT-FLOW-PROVIDER-TEST-CONTRACT-client-api") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(providerUrl)
                    }
                    branch(branch)
                    extensions {}
                }
            }
             scriptPath("Jenkinsfile")
        }
    }
}

pipelineJob("3-PACT-FLOW-CONSUMER-CAN-I-DEPLOY") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(consumerUrl)
                    }
                    branch(branch)
                    extensions {}
                }
            }
             scriptPath("Jenkinsfile-can-i-deploy")
        }
    }
}

pipelineJob("PROVIDER-CHANGED-CONTRACT-CHECK-INTEGRATION") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(providerUrl)
                    }
                    branch(branch)
                    extensions {}
                }
            }
             scriptPath("Jenkinsfile-can-i-deploy")
        }
    }
}