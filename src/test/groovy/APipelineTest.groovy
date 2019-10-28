import com.swedbank.jenkins.utilities.PipelineTestRunner
import spock.lang.Specification

class APipelineTest extends Specification {
    PipelineTestRunner runner

    def setup() {
        runner = new PipelineTestRunner()
        runner.preferClassLoading = false
    }
    def 'Check if it is correct pipeline loaded'() {
        when:
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
        }
        scriptStep()
        then:
        assert runner.helper.callStack[0].target.class.canonicalName == "aPipeline"
    }

    def 'Check if shell commands are called'() {
        given:
        List testingSH = []

        when:
        def scriptStep = runner.load {
            sharedLibrary("jenkins-pipelines-koans")
            script 'vars/aPipeline.groovy'
            property 'any', { null }
            method('sh', [String]) { str -> testingSH.add(str) }
        }
        scriptStep()

        then:
        assert testingSH[0] == 'gradle build'
        assert testingSH[1] == 'gradle test'
    }

    def 'Check if status is success'() {
        given:
        List testingEcho = []

        when:
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
            method('echo', [String]) {str -> testingEcho.add(str)}
        }
        scriptStep()

        then:
        assert testingEcho.contains('SUCCESS')
    }

    def 'Check if status is failure if a stage fails'() {
        given:
        List testingEcho = []

        when:
        Object currentResult = runner.binding.getVariable('currentBuild')
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
            method('sh', [String]) { str ->
                if (str.contains('test')) {
                    currentResult.result = 'FAILURE'
                } }
            method('echo', [String]) {str -> testingEcho.add(str)}
        }
        scriptStep()

        then:
        assert testingEcho.contains('FAIL')
    }
}
