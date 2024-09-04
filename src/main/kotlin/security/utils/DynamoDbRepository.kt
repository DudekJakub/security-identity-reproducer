package security.utils

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@ApplicationScoped
class DynamoDbRepository(
    enhancedDynamoClient: DynamoDbEnhancedAsyncClient,
) {
    private val tableSchema = TableSchema.fromClass(User::class.java)
    private val table = enhancedDynamoClient.table("Table", tableSchema)

    fun getUser(): Uni<User?> {
        return Uni.createFrom().completionStage { table.getItem { it.key { key -> key.partitionValue("jakub@gmail.com").build() } } }
    }

    fun saveUser(): Uni<Unit> {
        val user = User(email = "jakub@gmail.com", roles = setOf("admin"))
        return Uni.createFrom().completionStage { table.putItem { it.item(user) } }.replaceWithUnit()
    }
}