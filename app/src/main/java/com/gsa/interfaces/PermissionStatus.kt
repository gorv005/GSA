package com.gsa.interfaces

import androidx.annotation.IntDef
import com.gsa.common.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.gsa.common.CommonInt.DENIED
import com.gsa.common.CommonInt.GRANTED


import java.lang.annotation.RetentionPolicy
import kotlin.annotation.Retention

@Retention(AnnotationRetention.SOURCE)
@IntDef(GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED)
annotation class PermissionStatus

