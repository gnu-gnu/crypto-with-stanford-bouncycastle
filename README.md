# crypto-with-stanford-bouncycastle

simple crypto example

frontend(encrypt) using stanford javascript crypto - http://www-cs-students.stanford.edu/~tjw/jsbn/

backend(decrypt) using bouncycastyle Java Lightweight API -https://www.bouncycastle.org/java.html 

http://localhost:9009/server/ - mainpage

http://localhost:9009/server/generateKey - generate asymmetric key pair. exposes publickey to frontend and stores privatekey in backend.

http://localhost:9009/server/decrypt - encrypt plaintext at frontend side, send encrypted text to backend and callback decrypted text.
