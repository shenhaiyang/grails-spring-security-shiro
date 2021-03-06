/* Copyright 2013-2015 the original author or authors.
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
package grails.plugin.springsecurity.shiro

import grails.plugin.springsecurity.web.SecurityRequestHolder
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.realm.Realm
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
@CompileStatic
@Slf4j
class ShiroSpringSecurityEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

	/** Dependency injection for the realm. */
	Realm realm

	/** Dependency injection for the security manager. */
	SecurityManager securityManager

	void onApplicationEvent(AbstractAuthenticationEvent event) {

		log event

		if (event instanceof AuthenticationSuccessEvent || event instanceof InteractiveAuthenticationSuccessEvent) {
			ShiroUtils.bindSubject event.authentication, realm, securityManager,
					SecurityRequestHolder.request, SecurityRequestHolder.response
		}
		else if (event instanceof AuthenticationSwitchUserEvent) {
			// TODO
		}
	}

	protected void log(AbstractAuthenticationEvent event) {
		log.debug 'on{} for Authentication {}', event.authentication, event.class.simpleName
	}
}
