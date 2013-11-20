#client.R

library(rzmq)

remote.exec <- function(socket,fun,...) {
    send.raw.string( socket,data='hello world')
    receive.socket(socket)
}

substitute(expr)
context = init.context()
socket = init.socket(context,"ZMQ_REQ")
connect.socket(socket,"tcp://localhost:5555")

ans <- remote.exec(socket,sqrt,'hello world')
print(ans)
