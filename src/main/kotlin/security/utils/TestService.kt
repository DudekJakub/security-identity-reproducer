package security.utils

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TestService(
    private val repository: DynamoDbRepository
) {

    fun getUser(): Uni<User?> {
        return repository.getUser()
    }

    fun saveUser(): Uni<Unit> {
        return repository.saveUser()
    }
}