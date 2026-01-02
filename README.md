This is the documentation to help whoever interested to run this API locally or make call to API where it is currently hosted. 
It will also contain links to all the resources such as Github, Dockerhub and EC2 where resources are sitting.

THIS GUIDE IS FOR UBUNTU USERS.

Note:
This API will only accept WAV format with 16hz frequency. The size limit is set to 2MB.  

RESOURCE:
---------
GitHub: https://github.com/hossein-fazeli-git/SpeechToText

CI/CD APPROACH:
---------------
I use Github actions to deploy the resources to EC2 instance. The trigger for the workflow is push event.
The workflow has three major steps: Build and Test -> Build and Push -> Deploy
"Build and Push" step will build the code and docker images and push them to docker repo 
"Deploy" has dependency on success of "Build and Push" step, and it will ssh to EC2 instance and pull the latest
version of images from docker repo and then will run docker compose file to spawn containers.
Please note that all the sensitive data such as ssh_tokens are stored a environment variables in Github. 
Also, you can find the workflow configuration YAML file under .github/workflows

API ENDPOINTS:
--------------
/details/health 
    will return detailed health report of the system
/health
    will return OK
/api/convert/speech/to/text
    this API will accept a file of format WAV of up to 2MB and return the converted text 

HOW TO DOWNLOAD SAMPLE WAV FILES FOR TESTING:
---------------------------------------------
You need to download this sample WAV files and put them in the location of your choice in your local machine:  
large wav file: https://raw.githubusercontent.com/x4nth055/pythoncode-tutorials/refs/heads/master/machine-learning/speech-recognition/7601-291468-0006.wav  
small wav file: https://raw.githubusercontent.com/x4nth055/pythoncode-tutorials/refs/heads/master/machine-learning/speech-recognition/16-122828-0002.wav

HOW TO DOWNLOAD FROM GITHUB AND RUN LOCALLY:
--------------------------------------------
- mkdir SpeechToText
- cd SpeechToText
- git init
- git remote add origin https://github.com/hossein-fazeli-git/SpeechToText.git
- git pull
- git checkout main
- mvn install
- mvn spring-boot:run
- curl -X POST   http://localhost:8080/api/convert/speech/to/text  -H "Content-Type: multipart/form-data"  -F "audioFile=@/path/to/your/wav/file/sample_audio_large.wav"


HOW TO TEST THE SERVICE IN AWS EC2:
-----------------------------------
Currently, this API is deployed to an EC2 instance and you can call it using the following commands to test different APIs
- curl -X GET http://ec2-18-191-33-83.us-east-2.compute.amazonaws.com/health
- curl -X GET http://ec2-18-191-33-83.us-east-2.compute.amazonaws.com/details/health
- curl -X POST  http://ec2-18-191-33-83.us-east-2.compute.amazonaws.com/api/convert/speech/to/text  -H "Content-Type: multipart/form-data"  -F "audioFile=@/path/to/your/wav/file/sample_audio_large.wav"


HOW TO SSH to AWS EC2 TO SEE IMAGES AND CONTAINERS:
---------------------------------------------------
Please run the following command:

- echo "-----BEGIN RSA PRIVATE KEY-----
MIIEowIBAAKCAQEA1RWOh+xCSjltiP06HqfTUF/5c8JNIFrrVAS5IjhM9cAUZMST
NxHrUZJ/C8PkfSBEt6ACLIwNlsjcnZ7kve9kJ6DAhjA6lhsFpsq57S826jqv37UG
uVG+TycWDSTtF2TOet51uKTCw0gWuKI0OO9zHEi9ClfB/VH0gt/1KbAD0hyiC5Xu
nCrGN6qwBWKM8iSdU9ktJg2etI3esFgGOrpQsjyE6kT5IGks8qc2VigtxyQGmTLv
t0wyD8XQgpzhP2PgnUXE0i5xNlwLtQ/m/7nP2RBsKqS0983jyt2b97XyFqVLMW0u
V1GvWk5uxSCD1sjv2N6w5TEyNyvnyRJAXQwvlQIDAQABAoIBAA+CTCd0eLayS1in
Ja3JhUXaVmuofriWJX6uAVOOSXuxiUp0TEq70SbUxM50aZL54EaFLONVJkANSpFm
WTvBysZfmuOpavo16Ulkg/fRcevGbbqYkG64lmNocuBuCZukSKsEugjAZTSVcKHr
nGVweC5c1PWFwabQaj2EU3G/T+fg74DNCh1++qoB6kHhPH3s86BrjR4ZmbE3wn7t
zmJrDssuJnAOYrf6qNECtI7lbPI740PD7qmTZUCRt2AObBh5+2kC3jKFaUp050Ud
F+Gv40GTQO6XbyZLSDwcvnBI4Z0WJ3jk8nLmifQOesb8WpkYfYCRG7oyy1kIUVh9
MExK7UECgYEA/xvV4j+FKkU53sc8uPa9zUpB9Zvmv+W/ceQ2nqP5YwK2HG7rdf15
GguMfVhuh5kOldY6rfVbk0QigGbwz5NfwVyUg/Pcy3OlpN4c0mtXuu/ZIUNmLmem
ovzrXQZf6SwgXs0+pVqQGZ1F5EoRttKhcPSrA87BY/EEuLKHX2/W+BkCgYEA1dQi
pG4xCDKY8odFlHuzJ1Yqnb5i/ZHeOA7FWIyxnxoPf6fZvS++jG0x0sYElEXeMEU2
mNWv2fC5iObfeb24/ey9UVLokvvOPs1FCGiaD64nKix1ESaCkamBnoZ24rSl9BA4
qPtqRg/f7C/A5WlQrmKc2eJ1FvNp7XYmzD0AUt0CgYBp6Nx6+XQHgIXt/zkucQGL
YPDRTtAViwRX/C7Q3bKDcHS6iTIn4fuM3I6O20/H67sqaa4i2mISWY8ZT6NaCg0O
8aiWv39C19hFAhFlKjtZ2ImETb72NQHxWJ8+F49p9m/Z68u+SkLY33pMtJN2Gyv7
ARielA+ltkIHi9a3wG62wQKBgDkqgqx1LTSk2lEUqxx8SbQ9jB0qFir2gXN5N3dp
/d6sR/J3/HVgu0+trcFM4VKGr1U2W9dWYbIhcmjQWHSirT9tuFtyj+0j1srhdBBA
bte8Voom4pFYO1YX/ChWiJgpdaifYZaDyLC8c8AgMDDlDAXjJU33BhjBsNMkYFEA
CgMdAoGBAKqMvTf/Fqovfg4O/wq9ualGizafj5jvSXjiHr+fAuIvatY+ytbPMZoD
9C94fIrYfC0QinAYyINF0CdvgW9jDPey2B1VsUIC4sO3H/2PQCXbSUsxW8yUfpq0
F2LpGm5la271mQ/cZiYhYaudVJGfzG8IW04a41iZQqPpjZ0h5scs
-----END RSA PRIVATE KEY-----" > ssh_key.pem

- chmod 400 ssh_key.pem
- ssh -i "ssh_key.pem" ubuntu@ec2-18-191-33-83.us-east-2.compute.amazonaws.com
- sudo docker image ls
- sudo docker ps -a 



