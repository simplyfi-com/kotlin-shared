package com.simplyfi.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.simplyfi.sample.models.Route
import com.simplyfi.sdk.Client
import com.simplyfi.sdk.Config
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
actual fun Registration(config: Config, callback: (String, Route) -> Unit) {
    val client = Client(config)
    var companyName by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val authority = "DEDD"
    var tln by rememberSaveable { mutableStateOf("") }
    var secret by rememberSaveable { mutableStateOf("") }
    var compareSecret by rememberSaveable { mutableStateOf("") }
    val expiration = Clock.System.now() + 7.toDuration(DurationUnit.DAYS)

    val composableScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Register",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displaySmall
                )
            }

            Column {
                Button(
                    onClick = {
                        tln = randomString(5, digits)
                        firstName = randomString(6, letters)
                        lastName = randomString(6, letters)
                        companyName = randomString(6, letters) + " " + randomString(4, letters)
                        email = randomString(6, letters) + "@company.com"
                        phone = "971;58" + randomString(7, digits)
                        secret = randomString(6, letters) + randomString(2, digits) + randomString(
                            1,
                            symbols
                        )
                        compareSecret = secret
                    }
                ) {
                    Text(
                        text = "Random",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = companyName,
            onValueChange = { companyName = it },
            label = {
                Text(
                    text = "Company Name",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(
                    text = "First Name",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(
                    text = "Last Name",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = phone,
            onValueChange = { phone = it },
            label = {
                Text(
                    text = "Phone",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = tln,
            onValueChange = { tln = it },
            label = {
                Text(
                    text = "CR",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = secret,
            onValueChange = { secret = it },
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = compareSecret,
            onValueChange = { compareSecret = it },
            label = {
                Text(
                    text = "Repeat Password",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = {
                composableScope.launch {
                    val result = client.routines.execute(
                        "GW.05.0002.00.00.0.00.00.001",
                        companyName,
                        authority,
                        tln,
                        firstName,
                        lastName,
                        email,
                        phone,
                        secret,
                        expiration.toEpochMilliseconds().toString()
                    )

                    callback(result.message!!, Route.Onboarding)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}