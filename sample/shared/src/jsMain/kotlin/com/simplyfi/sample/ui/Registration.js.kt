package com.simplyfi.sample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.simplyfi.sample.models.Route
import com.simplyfi.sample.ui.digits
import com.simplyfi.sample.ui.letters
import com.simplyfi.sample.ui.randomString
import com.simplyfi.sample.ui.symbols
import com.simplyfi.sdk.Client
import com.simplyfi.sdk.Config
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.form
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.EmailInput
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.PasswordInput
import org.jetbrains.compose.web.dom.TelInput
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
actual fun Registration(
    config: Config,
    callback: (String, Route) -> Unit
) {
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

    Div {
        Div {
            H2 {
                Text("Register")
            }

            Button({
                onClick {
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
            }) {
                Text("Random")
            }
        }

        Div {
            Form {
                Div {
                    Label(forId = "companyName") {
                        Text("Company name")
                    }
                    TextInput {
                        id("companyName")
                        value(companyName)
                        onChange { companyName = it.value }
                    }
                }
                Div {
                    Label(forId = "firstName") {
                        Text("First name")
                    }
                    TextInput {
                        id("firstName")
                        value(firstName)
                        onChange { firstName = it.value }
                    }
                }
                Div {
                    Label(forId = "lastName") {
                        Text("Last name")
                    }
                    TextInput {
                        id("lastName")
                        value(lastName)
                        onChange { lastName = it.value }
                    }
                }
                Div {
                    Label(forId = "phone") {
                        Text("Phone")
                    }
                    TelInput {
                        id("phone")
                        value(phone)
                        onChange { phone = it.value }
                    }
                }
                Div {
                    Label(forId = "email") {
                        Text("Phone")
                    }
                    EmailInput {
                        id("email")
                        value(email)
                        onChange { email = it.value }
                    }
                }
                Div {
                    Label(forId = "tln") {
                        Text("CR")
                    }
                    TextInput {
                        id("tln")
                        value(tln)
                        onChange { tln = it.value }
                    }
                }
                Div {
                    Label(forId = "secret") {
                        Text("Password")
                    }
                    PasswordInput {
                        id("secret")
                        value(secret)
                        onChange { secret = it.value }
                    }
                }
                Div {
                    Label(forId = "compareSecret") {
                        Text("Repeat Password")
                    }
                    PasswordInput {
                        id("compareSecret")
                        value(compareSecret)
                        onChange { compareSecret = it.value }
                    }
                }
            }

            Div {
                Button(attrs = {
                    form("register")
                    type(ButtonType.Submit)
                    onClick {
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
                    }
                }) {
                    Text("Submit")
                }
            }
        }
    }
}