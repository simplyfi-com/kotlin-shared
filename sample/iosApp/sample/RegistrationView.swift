//
//  RegistrationView.swift
//  sample
//
//  Created by German Arutyunov on 26.01.2024.
//

import SwiftUI
import sdk

struct RegistrationView: View {
    @Binding var path: Destination
    @Binding var token: String
    
    @State private var companyName: String = ""
    @State private var firstName: String = ""
    @State private var lastName: String = ""
    @State private var phone: String = ""
    @State private var email: String = ""
    @State private var tln: String = ""
    @State private var secret: String = ""
    @State private var compareSecret: String = ""
    private let authority: String = "DEDD"
    private var expiration: Int {
        Int(Calendar.current.date(byAdding: .day, value: 7, to: Date())!.timeIntervalSince1970 * 1000)
    }
    
    var body: some View {
        NavigationStack {
            VStack {
                Form {
                    TextField("Company Name", text: $companyName)
                    TextField("First Name", text: $firstName)
                    TextField("Last Name", text: $lastName)
                    TextField("Phone", text: $phone)
                    TextField("Email", text: $email)
                    TextField("CR", text: $tln)
                    TextField("Password", text: $secret)
                    TextField("Repeat Password", text: $compareSecret)
                }
                Spacer()
                Button {
                    submit()
                } label: {
                    Text("Submit")
                }
            }
            .navigationTitle("Register")
            .toolbar {
                Button {
                    random()
                } label: {
                    Text("Random")
                }
            }
        }
    }
    
    @MainActor
    func submit() {
        let client = Client(
            config: ClientConfig(
                baseUrl: resourceString("API_URL"),
                apiKey: resourceString("API_KEY"),
                timeout: 180_000
            )
        )
        
        let paramsRaw = [
            companyName,
            authority,
            tln,
            firstName,
            lastName,
            email,
            phone,
            secret,
            String(expiration)
        ]
        
        let params = KotlinArray<NSString>(size: Int32(paramsRaw.count)) {
            NSString(utf8String: paramsRaw[$0.intValue])
        }
        
        Task {
            let res = try await client.routines.execute(
                name: "GW.05.0002.00.00.0.00.00.001",
                payload: RoutineExecute(params: params),
                token: nil
            )
            if let t = res.message {
                token = t
                path = .onboarding
            }
        }
    }
    
    func random() {
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
}
