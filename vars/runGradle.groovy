def call(params = [:]) {
    def build_status = 'ok'
    def defaultGradleArgs = params.get('defaultGradleArgs', '')
    def command = params.get('command', '')
    def exit_code = sh "gradle ${defaultGradleArgs} --stacktrace ${command}"
    if (exit_code != 0) {
        build_status = "Current stage failed in build ${env.BUILD_ID} with exit code ${exit_code}"
    }
    return build_status
}