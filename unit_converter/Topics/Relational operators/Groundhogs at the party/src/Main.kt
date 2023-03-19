fun main() {
    // write your code here
    val peanutButterCups = readln().toUInt()
    val isWeekend = readln().toBoolean()
    val isSuccessfulPartyWeekday = (peanutButterCups >= 10u) &&
            (peanutButterCups <= 20u) && !(isWeekend)
    val isSuccessfulPartyWeekend = (peanutButterCups >= 15u) &&
            (peanutButterCups <= 25u) && isWeekend
    val isSuccessfulParty = isSuccessfulPartyWeekday || isSuccessfulPartyWeekend
    println(isSuccessfulParty)
    
}