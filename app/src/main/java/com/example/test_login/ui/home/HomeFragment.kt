package com.example.test_login.ui.home

import MyPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 TabLayout 和 ViewPager2
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        // 创建并设置 ViewPager2 的适配器
        val adapter = MyPagerAdapter(requireActivity(), lifecycle)
        viewPager.adapter = adapter


        // 绑定 TabLayout 和 ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "系統公告"
                1 -> tab.text = "常用單張"
                2 -> tab.text = "將結案之個案"
            }
        }.attach()

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
