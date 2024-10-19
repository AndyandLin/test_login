package com.example.test_login.ui.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClosingAgreementViewModel : ViewModel() {

    // 用於存儲和管理用戶輸入的數據
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _closingDate = MutableLiveData<String>()
    val closingDate: LiveData<String> get() = _closingDate

    private val _idNumber = MutableLiveData<String>()
    val idNumber: LiveData<String> get() = _idNumber

    private val _closingType = MutableLiveData<Int>()
    val closingType: LiveData<Int> get() = _closingType

    private val _mentalStatus = MutableLiveData<Int>()
    val mentalStatus: LiveData<Int> get() = _mentalStatus

    private val _closingReason = MutableLiveData<Int>()
    val closingReason: LiveData<Int> get() = _closingReason

    private val _lateralLink = MutableLiveData<Int>()
    val lateralLink: LiveData<Int> get() = _lateralLink

    // 提交數據的方法
    fun submitData(name: String, closingDate: String, idNumber: String, closingType: Int, mentalStatus: Int, closingReason: Int, lateralLink: Int) {
        _name.value = name
        _closingDate.value = closingDate
        _idNumber.value = idNumber
        _closingType.value = closingType
        _mentalStatus.value = mentalStatus
        _closingReason.value = closingReason
        _lateralLink.value = lateralLink

        // TODO: 在這裡添加數據提交邏輯，例如發送到後端或保存到數據庫
    }
}