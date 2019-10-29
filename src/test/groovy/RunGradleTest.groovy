import com.swedbank.jenkins.utilities.PipelineTestRunner
import spock.lang.Specification

class RunGradleTest extends Specification{
    PipelineTestRunner runner

    def setup() {
        runner = new PipelineTestRunner()
        runner.preferClassLoading = false
    }

    /*
     * Testing library methods is the similar process as testing pipelines.
     * You can also verify what does the script return.
     */
    def 'check if script with empty parameters is working fine'() {
        when:
        def stepScript = runner.load {
            script 'vars/runGradle.groovy'
        }
        stepScript()

        then:
        assert stepScript.class.canonicalName == 'TODO: script name'
        assert stepScript() == 'TODO: status'
    }

    /*
     * The script returns 'build_status'. What will the status we get if 'sh' command fails.
     */
    def 'verify build status is not ok'() {
        when:
        def stepScript = runner.load {
            script 'vars/runGradle.groovy'
            env += ['BUILD_ID': '1']
            method ('sh', [String]) { return 'TODO: return any non zero value' }
        }
        stepScript()

        then:
        assert stepScript() == 'TODO: provide correct build_status'
        assert stepScript.env.BUILD_ID == '1'
    }
}
