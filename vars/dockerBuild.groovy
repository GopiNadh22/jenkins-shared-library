def call(String project, String hubUser, String imageTag) {

    echo "======================================"
    echo "DOCKER BUILD"
    echo "======================================"

    sh """
        docker build \
        -t ${hubUser}/${project}:${imageTag} .
    """


    echo "======================================"
    echo "TAGGING DOCKER IMAGE"
    echo "======================================"

    sh """
        docker tag \
        ${hubUser}/${project}:${imageTag} \
        ${hubUser}/${project}:latest
    """


    echo "======================================"
    echo "DOCKER LOGIN"
    echo "======================================"

    withCredentials([
        usernamePassword(
            credentialsId: 'docker_cred',
            usernameVariable: 'USER',
            passwordVariable: 'PASS'
        )
    ]) {

        sh '''
            echo "$PASS" | docker login \
            -u "$USER" \
            --password-stdin
        '''
    }


    echo "======================================"
    echo "PUSHING IMAGE"
    echo "======================================"

    sh """
        docker push ${hubUser}/${project}:${imageTag}
    """

    sh """
        docker push ${hubUser}/${project}:latest
    """
}
