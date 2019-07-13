package com.britt.listi.usecase

expect class MakeCall(){
    fun execute(callee: String)
}

fun callIfHasNumber(memberId: String){

    val isTimeForCall = true
    if (isTimeForCall)
        MakeCall().execute(memberId)

}
