/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.more.core.log;
/**
 * 日志异常
 * @version 2009-5-13
 * @author 赵永春 (zyc@byshell.org)
 */
public class LogException extends RuntimeException {
    private static final long serialVersionUID = 8222826886737695884L;
    // ===============================================================
    /** 日志异常 */
    public LogException() {
        super("日志异常");
    }
    /** 日志异常，错误信息由参数给出 */
    public LogException(String msg) {
        super(msg);
    }
    /** 日志异常，错误信息是承接上一个异常而来 */
    public LogException(Exception e) {
        super(e);
    }
}