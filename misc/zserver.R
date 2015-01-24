#server.R

library(rzmq)

handle1 <- function() {
    msg = receive.string(socket);
    print(msg)
	a <- system('ls')
    #send.raw.string(socket, 'done')
    send.socket(socket, a)
}

handle <- function() {
    msg = receive.socket(socket);
    fun <- msg$fun
    args <- msg$args
    print(args)
    ans <- do.call(fun,args)
    send.socket(socket,ans);
}

context = init.context()
socket = init.socket(context,"ZMQ_REP")
bind.socket(socket,"tcp://*:5555")
while(1) {
	handle1()
}
