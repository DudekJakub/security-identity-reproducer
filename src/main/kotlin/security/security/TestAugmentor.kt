package security.security

import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.SecurityIdentityAugmentor
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import security.utils.TestService

@ApplicationScoped
class TestAugmentor(
    private val testService: TestService
) : SecurityIdentityAugmentor {

    override fun augment(identity: SecurityIdentity, context: AuthenticationRequestContext): Uni<SecurityIdentity> {
        return testService.getUser()
            .map { user ->
                if (user == null) return@map identity
                val builder = QuarkusSecurityIdentity.builder(identity)
                builder.addRoles(user.roles)
                builder.build()
            }
    }
}
