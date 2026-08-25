def call(String project, String hubUser, String imageTag) {

    echo "======================================"
    echo "DOCKER CLEANUP"
    echo "======================================"

    sh """
        docker image rm \
        ${hubUser}/${project}:${imageTag} \
        || true
    """

    sh """
        docker image rm \
        ${hubUser}/${project}:latest \
        || true
    """

    sh """
        docker image prune -f
    """
}
