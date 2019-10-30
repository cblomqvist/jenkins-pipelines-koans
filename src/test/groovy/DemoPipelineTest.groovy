import com.swedbank.jenkins.utilities.PipelineTestRunner
import spock.lang.Specification

class DemoPipelineTest extends Specification {

    PipelineTestRunner runner

    def setup() {
        runner = new PipelineTestRunner()
        runner.preferClassLoading = false
    }

    def 'check if pipeline is loaded correctly'() {
        given:
        def stepScript = runner.load {
            script 'vars/demoPipeline.groovy'
        }

        when:
        stepScript()

        then:
        assert stepScript.class.canonicalName == 'demoPipeline'
    }

    def 'check if sh command is called'() {
        given:
        List testSh = []
        def stepScript = runner.load {
            script 'vars/demoPipeline.groovy'
            method ('sh', [String]) {
                str -> testSh.add(str)
            }
        }

        when:
        stepScript()

        then:
        assert testSh.contains('gradle build')
        assert testSh.size() == 1
    }

    def 'fail the test stage'() {
        given:
        Object currentBuild = runner.binding.getVariable('currentBuild')
        List testEcho = []
        def stepScript = runner.load {
            script 'vars/demoPipeline.groovy'
            method ('sh', [String]) { str ->
                if (str.contains('test')) {
                    currentBuild.result = 'FAILURE'
                }
            }
            method ('echo', [String]) {
                str -> testEcho.add(str)
            }
        }

        when:
        stepScript()

        then:
        testEcho.contains('Boo')

    }
}
