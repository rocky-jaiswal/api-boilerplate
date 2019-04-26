#!/usr/bin/ruby

require 'securerandom'
require 'openssl'
require 'base64'

environment = ARGV[0] || 'development'

r1 = SecureRandom.hex(32)[0, 32]
r2 = SecureRandom.hex(16)[0, 16]

cipher = OpenSSL::Cipher::AES.new(256, :CBC)
cipher.encrypt

cipher.key = r1
cipher.iv = r2

encrypted = ""
File.readlines('src/main/resources/secrets/secrets.yaml').each do |line|
    encrypted << cipher.update(line)
end
encrypted << cipher.final

File.write('src/main/resources/secrets/secrets.enc', Base64.encode64(encrypted))

# Write secrets to properties file
properties = <<~HEREDOC
                   systemProp.application.environment=#{environment}
                   systemProp.application.key=#{r1}
                   systemProp.application.iv=#{r2}
                HEREDOC

File.write('gradle.properties', properties)