pipeline {
    agent any
    
    tools {
        maven 'M3'
    }
    
    stages {
        stage('Cleaning workspace'){
            steps {
                sh 'echo "---=--- Cleaning Stage ---=---"'
                sh 'mvn clean'
                script {
                    try {
                        sh 'docker stop myboot && docker rm myboot'
                    } catch (Exception e) {
                        sh 'echo "---=--- No container to remove ---=---"'
                    }
                }
            }
        }
        stage('Checkout'){
            steps {
                sh 'echo "---=--- Checkout ---=---"'
                git branch: 'main', url: 'https://github.com/davidbatisson/simple_boot.git'
            }
        }
        stage('Compile'){
            steps {
                sh 'echo "---=--- Checkout ---=---"'
                sh 'mvn compile'
            }
        }
        stage('Test'){
            steps {
                sh 'echo "---=--- Test ---=---"'
                sh 'mvn test'
            }
        }
        stage('Package'){
            steps {
                sh 'echo "---=--- Package ---=---"'
                sh 'mvn package -DskipTests'
            }
        }
		stage('SSH transfert') { 
			steps { 
				script { 
					sshPublisher( 
						publishers: [ 
							sshPublisherDesc(configName: 'ec2-host', transfers:[ 
								sshTransfer( 
										execCommand: ''' 
										echo "-=- Cleaning project -=-" 
										sudo docker stop simple-boot-cont || true 
										sudo docker rm simple-boot-cont || true 
										sudo docker rmi simple-boot || true 
									''' 
								), 
								sshTransfer( 
									sourceFiles:"target/*.jar", 
									removePrefix: "target", 
									remoteDirectory: "//home//ec2-user", 
									execCommand: "ls /home/ec2-user" 
								), 
								sshTransfer( 
									sourceFiles:"Dockerfile", 
									removePrefix: "", 
									remoteDirectory: "//home//ec2-user", 
									execCommand: ''' 
										cd /home/ec2-user; 
										sudo docker build -t simple-boot .; 
										sudo docker run -d --name simple-boot-cont -p 8080:8080 simple-boot; 
									''' 
								) 
							]) 
						]
					) 
				} 
			}
		}
    }
}