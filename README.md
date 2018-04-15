# crypto-with-stanford-bouncycastle

## simple crypto example

#### frontend(encrypt) uses stanford javascript crypto - http://www-cs-students.stanford.edu/~tjw/jsbn/

#### backend(decrypt) uses bouncycastyle Java Lightweight API -https://www.bouncycastle.org/java.html

----

## features

#### mainpage - http://localhost:9009/server/

#### generate asymmetric key pair. exposes publickey to frontend and stores privatekey in backend. - http://localhost:9009/server/generateKey

#### encrypt plaintext at frontend side, send encrypted text to backend and callback decrypted text. - http://localhost:9009/server/decrypt
