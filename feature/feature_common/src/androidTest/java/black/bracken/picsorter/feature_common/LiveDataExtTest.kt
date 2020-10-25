package black.bracken.picsorter.feature_common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import black.bracken.picsorter.feature_common.ext.observeOnce
import io.mockk.Called
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LiveDataExtTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TestViewModel
    private lateinit var lifecycle: Lifecycle
    private lateinit var onChanged: (Int) -> Unit

    @Before
    fun setUp() {
        viewModel = TestViewModel()
        lifecycle = LifecycleRegistry(mockk()).apply {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
        onChanged = mockk(relaxed = true)
    }

    @After
    fun teardown() {
        confirmVerified()
    }

    @Test
    fun observeOnce_callOnlyOnce() {
        viewModel.liveData.observeOnce(LifecycleOwner { lifecycle }, onChanged)
        repeat(5) { viewModel.increment() }

        verify(exactly = 1) { onChanged(any()) }
    }

    @Test
    fun observeOnce_noCalling() {
        viewModel.liveData.observeOnce(LifecycleOwner { lifecycle }, onChanged)

        verify { onChanged(any()) wasNot Called }
    }

    class TestViewModel : ViewModel() {
        private val _liveData = MutableLiveData(0)
        val liveData: LiveData<Int> get() = _liveData

        fun increment() {
            _liveData.value = (liveData.value ?: 0) + 1
        }
    }

}