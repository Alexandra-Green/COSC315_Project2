public class Buffer {
//Each Request has two attributes time. Time request takes and its ID
int rID;
long length;

Buffer(){
	
}

Buffer(int num, long time){
	this.rID = num;
	this.length = time;
}
}
