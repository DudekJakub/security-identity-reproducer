package security.security

import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.SecurityIdentityAugmentor
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.uni
import jakarta.enterprise.context.ApplicationScoped
import security.utils.TestService

@ApplicationScoped
class TestAugmentor(
    private val testService: TestService
) : SecurityIdentityAugmentor {

    /** A) Below version of augment works WITH DynamoDb call and it throws ContextNotActiveException **/
    override fun augment(identity: SecurityIdentity, context: AuthenticationRequestContext): Uni<SecurityIdentity> {
        return testService.getUser()
            .map { user ->
                if (user == null) return@map identity
                val builder = QuarkusSecurityIdentity.builder(identity)
                builder.addRoles(user.roles)
                builder.build()
            }
    }

    /** B) Below version of augment works without DynamoDb call, and it allows to get access to GET /hello endpoint **/
//    override fun augment(identity: SecurityIdentity, context: AuthenticationRequestContext): Uni<SecurityIdentity> {
//        return uni {
//            QuarkusSecurityIdentity.builder(identity)
//                .addRoles(setOf("admin"))
//                .build()
//        }
//    }

    /** C) Below version of augment works without DynamoDb call, and it does not allow to get access to GET /hello endpoint **/
//    override fun augment(identity: SecurityIdentity, context: AuthenticationRequestContext): Uni<SecurityIdentity> {
//        return uni {
//            QuarkusSecurityIdentity.builder(identity)
//                .addRoles(setOf("user"))
//                .build()
//        }
//    }
}
