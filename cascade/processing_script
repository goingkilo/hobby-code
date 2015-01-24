
float xpos = 100, ypos = 100, zpos = 100, posd = 10;
float xtilt, ytilt, ztilt, tiltd = 1.5f;

void setup() {
   size( 600, 600, P3D);
   background(254);
   boxCity();
}

void draw() {
  background(254);

  translate( xpos, ypos, zpos);
  //rotate( 0, xpos, ypos, zpos);
  rotateX( radians( xtilt ));
  rotateY( radians( ytilt ));
  rotateZ( radians( ztilt ));
  fill(0);
  box(10);
  boxCity();
  //drawMovingBox();
  
}

void drawMovingBox(){
  pushMatrix();
  translate( xpos, ypos, zpos);
  fill( 0,0,100);
  box(10);
  popMatrix();
}

void boxCity() {
  for( int i = 0 ; i < 10 ; i ++ ) {
    for( int j = 0 ; j < 10 ; j ++ ) {
      for ( int k = 0 ; k < 10 ; k ++ ) {
        pushMatrix();
        translate( i*100,j*100,k*100);
        fill( 250 - i , 250 - k, 0);
        box( 10);
        popMatrix();
      }
    }
  }
} 
   

void keyPressed() {
  if( key == CODED){
    switch(keyCode) {
     case  UP : 
        zpos += posd;
        break;
      case DOWN:
        zpos -= posd;
        break;
      case LEFT:
        xpos += posd;
        break;
      case RIGHT :
        xpos -= posd;
         break;
      case java.awt.event.KeyEvent.VK_PAGE_DOWN :
        ypos -= posd;
        break;
       case java.awt.event.KeyEvent.VK_PAGE_UP :
         ypos += posd;
    }
  }
  else {
 
    switch( key) {
      case 'w':
       println( "tilt forward"); 
        xtilt  += tiltd;
        break;
      case 's':
        xtilt  -= tiltd;
        break;
      case 'a':
        ytilt += tiltd;
        break;
      case 'd':
        ytilt -= tiltd;
        break;
      case 'q':
        ztilt += tiltd;
        break;
      case 'e':
        ztilt -= tiltd;
        break;
      case 'r':
        xtilt = 0;
        ytilt = 0;
        ztilt = 0;
        break;
    }  
  }
}

void mouseClicked() {
  
}
