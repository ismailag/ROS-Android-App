Welcome to the ROS-Android-App wiki!
To try this App you have to run the server file on the robot side , therefore you have to install on the robot side :
* ROS Bridge  
`sudo apt-get install ros-<rosdistro>-rosbridge-server`
* Node js <br> 
`sudo apt-get install curl` <br>
`curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -` <br>
`sudo apt-get install -y nodejs`<br>
* Socket.io version 0.9.16 <br>
(the app is incompatible with newer versions ) <br>
```sudo apt-get install npm``` <br>
`npm install socket.io@0.9.16` <br>
* RoslibJs <br>
`sudo npm install roslib` <br>
<br>
Now to run the server file you should : <br>
* Run roscore <br>
`roscore &` <br>
* Run the rosbridge server <br>
`roslaunch rosbridge_server rosbridge_websocket.launch` <br>
* Run the nodejs server script <br>
`node filename.js` <br>
<h4>P.S</h4>
before running the server or compiling the app you should adapt some variables (the IP addresses ) to your environment .
