import com.swedbank.jenkins.utilities.PipelineTestRunner
import spock.lang.Specification

class APipelineTest extends Specification {
    PipelineTestRunner runner

    def setup() {
        runner = new PipelineTestRunner()
        runner.preferClassLoading = false
    }

    /*
     * Verify the loaded object class name is the pipeline script file name.
     */
    def 'Check if it is correct pipeline loaded'() {
        when:
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
        }
        scriptStep()
        then:
        assert scriptStep.class.canonicalName == "nPipeline"
    }

    /*
     * You can collect all methods calls into a data structure. In this test it is List []. All records in this List will
     * come at the call order.
     */
    def 'Check if shell commands are called'() {
        given:
        List testingSH = []

        when:
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
            method('sh', [String]) { str -> testingSH.add(str) }
        }
        scriptStep()

        then:
        assert testingSH.size() == 3
        assert testingSH[0] == 'TODO: provide correct string'
        assert testingSH[1] == 'TODO: provide correct string'
    }

    /*
     *  The pipeline post a result of execution. User can tell explicitly what he/she wants to publish by which build result
     *  by using post {} block. If there is nothing wrong, pipeline should publish a text given inside success{} closure.
     *  The task is similar to previous one. Collect all calls of 'echo' and check if there is one contains 'SUCCESS'
     */
    def 'Check if status is success'() {
        given:
        List testingEcho = []

        when:
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
            //TODO: Collect all 'echo' call into testingEcho list
        }
        scriptStep()

        then:
        assert testingEcho.contains('SUCCESS')
    }

    /*
     *  We can manipulate of a stage result to test needed behavior.
     *  Use 'currentBuild' binding variable to set build result
     */
    def 'Check if status is failure if a stage fails'() {
        given:
        List testingEcho = []

        when:
        Object currentResult = runner.binding.getVariable('currentBuild')
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'any', { null }
            method('sh', [String]) { str ->
                    //TODO: You don't want to fail all 'sh' but only one. Choose which.
                    currentResult.result = 'FAILURE' }
            method('echo', [String]) {str -> testingEcho.add(str)}
        }
        scriptStep()

        then:
        assert testingEcho.contains('FAIL')
    }

    /*
     * The framework might miss some properties/methods you need for pipeline. Sometimes you should to define them.
     */
    def 'Check if it is everything defined for run'() {
        when:
        def scriptStep = runner.load {
            script 'vars/aPipeline.groovy'
            property 'TODO: there is property missing for agent closure', { null }
        }
        scriptStep()
        then:
        assert runner.helper.callStack[0].target.class.canonicalName == "aPipeline"
    }
}
