import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test_login.ui.fragments.SystemAnnouncementsFragment
import com.example.test_login.ui.fragments.ClosedCasesFragment
import com.example.test_login.ui.fragments.CommonFormsFragment


class MyPagerAdapter(fragmentActivity: FragmentActivity, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3  // 假設有三個分頁

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SystemAnnouncementsFragment()
            1 -> CommonFormsFragment()
            2 -> ClosedCasesFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
