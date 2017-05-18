package com.nunez.libellis.entities

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "user_shelf", strict = false)
class Shelve(
        @field:Element var id:Int = 0,
        @field:Element var name:String = "")