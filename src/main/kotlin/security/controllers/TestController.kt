package security.controllers

import io.smallrye.mutiny.Uni
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType.TEXT_PLAIN
import security.utils.TestService
import security.utils.User

@Path("/hello")
class TestController(
    private val testService: TestService
) {

    @GET
//    @RolesAllowed("admin")
    @Produces(TEXT_PLAIN)
    fun hello1(): Uni<User?> {
        return testService.getUser()
    }

    @POST
    @Path("/save")
    fun saveUser(): Uni<Unit> {
        return testService.saveUser()
    }
}
