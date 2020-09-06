package utils

import com.ongdev.blog.api.utils.AppUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AppUtilsUnitTest {

    @InjectMocks
    lateinit var appUtils: AppUtils

    @Test
    fun testAppUtils() {
        var str = appUtils.removeAccent("Ngày đầu tiên đi học")
        assertEquals("ngay-dau-tien-di-hoc", str)
    }
}