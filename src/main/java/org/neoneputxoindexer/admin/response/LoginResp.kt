package org.neoneputxoindexer.admin.response

import org.neoneputxoindexer.model.Admin
import io.swagger.annotations.ApiModel

@ApiModel(value = "LoginResp")
class LoginResp(var token: String?, var id: Long, var admin: Admin?)
