import com.swedbank.jenkins.utilities.PipelineTestRunner
import spock.lang.Specification

class RunGradleTest extends Specification{
    PipelineTestRunner runner

    def setup() {
        runner = new PipelineTestRunner()
        runner.preferClassLoading = false
    }

    def 'check if script with empty parameters is working fine'() {
        when:
        def stepScript = runner.load {
            script 'vars/runGradle.groovy'
        }
        stepScript()

        then:
        assert stepScript.class.canonicalName == 'runGradle'
        assert stepScript() == 'ok'
    }

    def 'verify build status is not ok'() {
        when:
        def stepScript = runner.load {
            script 'vars/runGradle.groovy'
            env += ['BUILD_ID': '1']
            method ('sh', [String]) { return 1 }
        }
        stepScript()

        then:
        assert stepScript() == 'Current stage failed in build 1 with exit code 1'
        assert stepScript.env.BUILD_ID == '1'
    }
}
