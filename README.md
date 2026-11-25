# Java_For_Networks2

## Part 1 
The goal of this part is to create a TCP server that will wait for a client. When a client is connected, it will wait for some message from the client, and when it received some, it "acknowledge" it by retransmit the received message to the client. 

### Step 1 
We generate the bone of the server. 
NB : we added a boolean 'running' that can give us the possibility to start a server that is not running (just in case of)
### Step 2

### Step 3
After we finished the TCPServer, we tested it with our terminal.

![Test_TCP_Server_Step5_CMD_view.png](Diagram/Part1/Test_TCP_Server_Step5_CMD_view.png)

From the terminal view, we are able to connect ourself to the server and then we can write. The server retransmit what we send to it so at least it seems to work properly. 

![Test_TCP_Server_Step5_Server_View.png](Diagram/Part1/Test_TCP_Server_Step5_Server_View.png)

From the server view, we have the messages when the socket is created. When a client send a message, the server receive it. 

However, when we tested that the server remains open after the client disconnexion, we found the server send an error. Indeed, we did not manage well the closing of the sockets. After we manage this issue, we have the two following view and when the client leave, the server remains open and wait for another client. 

![Case_Working_After_Deconnection.png](Diagram/Part1/Case_Working_After_Deconnection.png)
