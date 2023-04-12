fun countStrings(list: List<Any>): Int {
    // make your code here
    return list.fold(0) { sum, element -> sum + if(element is String) 1 else 0 }
}