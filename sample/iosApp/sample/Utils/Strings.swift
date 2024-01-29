//
//  Strings.swift
//  sample
//
//  Created by German Arutyunov on 29.01.2024.
//

import Foundation

let letters = "abcdefghijklmnopqrstuvwxyz"
let digits = "123456789"
let symbols = "!$#&"

func randomString(_ length: Int, _ pool: String) -> String {
    String((0..<length).map{ _ in pool.randomElement()! })
}

func resourceString(_ name: String) -> String {
    guard let config = Bundle.main.infoDictionary else {
        fatalError("Config not found")
    }
    
    guard let value = config[name] as? String else {
        fatalError("\(name) not set")
    }
    
    return value
}
