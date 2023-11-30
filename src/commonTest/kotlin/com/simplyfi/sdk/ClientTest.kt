package com.simplyfi.sdk

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ClientTest {
    private lateinit var config: Config
    private lateinit var client: Client

    @BeforeTest
    fun setup() {
        config = Config(getEnv("API_URL")!!, getEnv("API_KEY")!!)
        client = Client(config)
    }

    @Test
    fun shouldExecuteRegisterRoutine() = runTest {
        val authority = "DEDD"
        val tln = randomString(5, digits)
        val fname = randomString(6, letters)
        val lname = randomString(6, letters)
        val companyName = randomString(6, letters) + " " + randomString(4, letters)
        val email = randomString(6, letters) + "@company.com"
        val phone = "971;58" + randomString(7, digits)
        val secret = randomString(6, letters) + randomString(2, digits) + randomString(1, symbols)
        val expiration = Clock.System.now() + 7.toDuration(DurationUnit.DAYS)

        val result = client.routines.execute(
            "PT.05.0002.00.00.0.00.00.001",
            companyName, authority, tln, fname, lname, email, phone, secret, expiration.toEpochMilliseconds().toString()
        )

        assertNotNull(result.message)
    }
}