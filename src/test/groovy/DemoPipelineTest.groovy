import com.swedbank.jenkins.utilities.PipelineTestRunner
import spock.lang.Specification

class DemoPipelineTest extends Specification {

    PipelineTestRunner runner

    def setup() {
        runner = new PipelineTestRunner()
        runner.preferClassLoading = false
    }
}
