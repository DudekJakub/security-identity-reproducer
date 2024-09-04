package security.utils

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class User(
    @get:DynamoDbPartitionKey
    var email: String,
    var roles: Set<String>
)
