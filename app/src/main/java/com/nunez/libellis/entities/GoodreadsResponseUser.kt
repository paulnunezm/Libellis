package com.nunez.libellis.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "user", strict = false)
class GoodreadsUserResponse(@field:Attribute(name = "id") var id: String)