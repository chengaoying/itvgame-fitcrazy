package com.tvgame.actor;

import javax.microedition.lcdui.*;
import java.io.*;

//import com.nokia.mid.ui.*;

//SPX3.4 utils class
//Copyright YOYO 2007
//opencai@126.com
//QQ: 77649603
//MSN: opencai@hotmail.com

public class SpriteX {
  //sprite file header
//  public static final int SPX_HEADER = 1397772888; //DFSPX

  //sprite class version
//  public static final byte SPX_VERSION = 34; //3.4

  //byte sequence
//  public static final byte SPX_BYTE_SEQUENCE_JAVA = 1;

  //public static final int SPX_BYTE_SEQUENCE_C = 0;

  //sprite transform type
  public static final byte TRANS_NONE = 0;
  public static final byte TRANS_ROT90 = 5;
  public static final byte TRANS_ROT180 = 3;
  public static final byte TRANS_ROT270 = 6;
  public static final byte TRANS_MIRROR = 2;
  public static final byte TRANS_MIRROR_ROT90 = 7;
  public static final byte TRANS_MIRROR_ROT180 = 1;
  public static final byte TRANS_MIRROR_ROT270 = 4;

  //rotate bit
  public static final byte ANTICLOCKWISE_90 = 0;
  public static final byte DEASIL_90 = 1;
  public static final byte HORIZONTAL = 2;
  public static final byte VERTICAL = 3;

  //sprite direction
  public static final byte DIRECTION_NONE = 0;
  public static final byte DIRECTION_UP = 1;
  public static final byte DIRECTION_DOWN = 2;
  public static final byte DIRECTION_LEFT = 3;
  public static final byte DIRECTION_RIGHT = 4;

  //frame attrib
  public static final byte FRAME_HEADER_SIZE = 8;

  //frame data bit
  public static final byte FRAME_TILE_COUNT_BIT = 1;
  public static final byte FRAME_COLLISION_COUNT_BIT = 2;
  public static final byte FRAME_REFERENCE_POINT_COUNT_BIT = 3;
  public static final byte FRAME_TOP_POS_BIT = 4;
  public static final byte FRAME_BOTTOM_POS_BIT = 5;
  public static final byte FRAME_LEFT_POS_BIT = 6;
  public static final byte FRAME_RIGHT_POS_BIT = 7;

  //action attrib
  public static final byte ACTION_HEADER_SIZE = 4;

  //action data bit
  public static final byte ACTION_SEQUENCE_LENGTH_BIT = 1;
  public static final byte ACTION_SEQUENCE_DELAY_BIT = 2;
  public static final byte ACTION_TRANSFORM_BIT = 3;

  //collision type
  public static final byte COLLISION_INTERSECT = 1;
  public static final byte COLLISION_INCLUSION = 2;

  public static final byte INVERTED_AXES = 0x4;

  //transfrom table
  public static final byte[][] TRANSFORM_TABLE = new byte[ /*8*/][ /*8*/] { {
      TRANS_NONE,
      TRANS_MIRROR_ROT180,
      TRANS_MIRROR, TRANS_ROT180,
      TRANS_MIRROR_ROT270,
      TRANS_ROT90, TRANS_ROT270,
      TRANS_MIRROR_ROT90}
      , {
      TRANS_MIRROR_ROT180,
      TRANS_NONE, TRANS_ROT180,
      TRANS_MIRROR, TRANS_ROT90,
      TRANS_MIRROR_ROT270,
      TRANS_MIRROR_ROT90,
      TRANS_ROT270}
      , {
      TRANS_MIRROR, TRANS_ROT180,
      TRANS_NONE,
      TRANS_MIRROR_ROT180,
      TRANS_ROT270,
      TRANS_MIRROR_ROT90,
      TRANS_MIRROR_ROT270,
      TRANS_ROT90}
      , {
      TRANS_ROT180, TRANS_MIRROR,
      TRANS_MIRROR_ROT180,
      TRANS_NONE,
      TRANS_MIRROR_ROT90,
      TRANS_ROT270, TRANS_ROT90,
      TRANS_MIRROR_ROT270}
      , {
      TRANS_MIRROR_ROT270,
      TRANS_ROT270, TRANS_ROT90,
      TRANS_MIRROR_ROT90,
      TRANS_NONE, TRANS_MIRROR,
      TRANS_MIRROR_ROT180,
      TRANS_ROT180}
      , {
      TRANS_ROT90,
      TRANS_MIRROR_ROT90,
      TRANS_MIRROR_ROT270,
      TRANS_ROT270,
      TRANS_MIRROR_ROT180,
      TRANS_ROT180, TRANS_NONE,
      TRANS_MIRROR}
      , {
      TRANS_ROT270,
      TRANS_MIRROR_ROT270,
      TRANS_MIRROR_ROT90,
      TRANS_ROT90,
      TRANS_MIRROR, TRANS_NONE,
      TRANS_ROT180,
      TRANS_MIRROR_ROT180}
      , {
      TRANS_MIRROR_ROT90,
      TRANS_ROT90, TRANS_ROT270,
      TRANS_MIRROR_ROT270,
      TRANS_ROT180,
      TRANS_MIRROR_ROT180,
      TRANS_MIRROR, TRANS_NONE}
  };

  //rotate table
  public static final byte ROTATE_TABLE[][] = new byte[ /*8*/][ /*4*/] { {
      TRANS_ROT270, TRANS_ROT90,
      TRANS_MIRROR,
      TRANS_MIRROR_ROT180}
      , {
      TRANS_MIRROR_ROT90,
      TRANS_MIRROR_ROT270,
      TRANS_ROT180, TRANS_NONE}
      , {
      TRANS_MIRROR_ROT270,
      TRANS_MIRROR_ROT90, TRANS_NONE,
      TRANS_ROT180}
      , {
      TRANS_ROT90, TRANS_ROT270,
      TRANS_MIRROR_ROT180,
      TRANS_MIRROR}
      , {
      TRANS_MIRROR_ROT180,
      TRANS_MIRROR, TRANS_ROT90,
      TRANS_ROT270}
      , {
      TRANS_NONE, TRANS_ROT180,
      TRANS_MIRROR_ROT270,
      TRANS_MIRROR_ROT90}
      , {
      TRANS_ROT180, TRANS_NONE,
      TRANS_MIRROR_ROT90,
      TRANS_MIRROR_ROT270}
      , {
      TRANS_MIRROR,
      TRANS_MIRROR_ROT180,
      TRANS_ROT270, TRANS_ROT90}
  };

  short[][] actionData; //action data
  byte actionCount; //action count
  short[][] frameData; //frame data
  byte frameCount; //frame count
  short[][] tileData; //tile data
  short tileCount; //tile count
  byte actionIndex; //current action
  byte sequenceIndex; //current action sequence
  Image image; //sprite image
  Image[] tiles;
  boolean tileMode;

  int x; //sprite x position
  int y; //sprite y position
  boolean visible; //sprite visible
  long lastTime; //last update sequence time
  boolean firstUpdate; //frist update sequence
  boolean disableUpdate; //disable update sequence

  //origin offset position
  int originOffsetX;
  int originOffsetY;

  public SpriteX(String spxName, Image image) {
    loadSpx(spxName, image);
  }

  public SpriteX(String spxName, Image[] tiles) {
    loadSpx(spxName, image);
    this.tiles = tiles;
    this.tileMode = true;
  }

  public boolean endAnimaiton()
  {
	  if(getFrame()==frameCount-1)
		  return true;
	  return false;
  }
  
  public SpriteX(String spxName, String imageName) {
    loadSpx(spxName, null);

    if (tileMode) {
      String name = null;
      String ext = null;
      char ch;
      int namelen = imageName.length();
      for (int i = namelen - 1; i >= 0; --i) {
        ch = imageName.charAt(i);
        if (ch == '.') {
          ext = imageName.substring(i);
          name = imageName.substring(0, i);
          break;
        }
      }
      if (ext == null) {
        name = imageName;
        ext = "";
      }

      tiles = new Image[tileCount];

      String fullName;
      for (int i = 0; i < tileCount; i++) {
        fullName = name + i + ext;

        Image spximg = null;
        try {
          spximg = Image.createImage(fullName);
        } catch (IOException ex) {
          System.out.println("can't load spx image\n");
        }
        tiles[i] = spximg;
      }

      this.tiles = tiles;
    } else {
      Image spximg = null;
      try {
        spximg = Image.createImage(imageName);
      } catch (IOException ex) {
        System.out.println("can't load spx image\n");
      }
      loadSpx(spxName, spximg);
    }
  }

  public void loadSpx(String spxName, Image image) {
    try {
      InputStream is = "".getClass().getResourceAsStream(spxName);
      DataInputStream data = new DataInputStream(is);
//      int header;
      byte sequenceLength;
      short collisionCount;
      short referencePointCount;
      short i, j, offset;
      short length;
      byte delay;
      short frameTileCount;
      byte version;
      byte byteSequence;

      //check spx format
      data.readInt();
//      if (header != SPX_HEADER) {
//        throw new Exception("invalid SpriteX format\n");
//      } else {
        version = data.readByte();
        //sprite file and sprite class no matching
//        if (version != SPX_VERSION) {
//          throw new Exception("version no matching\n");
//        }

        //byte sequence check
        byteSequence = data.readByte();
//        if ( (byteSequence & 1) != SPX_BYTE_SEQUENCE_JAVA) {
//          throw new Exception("byte sequence error\n");
//        }

        //add
        if ( (byteSequence & 2) != 0) {
          tileMode = true;
        }

        //read tile
        tileCount = (short)data.readInt();
//        System.out.println("tileCount:"+tileCount);
        if (tileMode == false) {
          tileData = new short[tileCount][4];
          //struct
          //...
          //array[0] tile x
          //array[1] tile y
          //array[2] tile width
          //array[3] tile height
          //...
          for (i = 0; i < tileCount; i++) {
            tileData[i][0] = data.readShort();
            tileData[i][1] = data.readShort();
            tileData[i][2] = data.readShort();
            tileData[i][3] = data.readShort();
          }
        }

        //read frame
        frameCount = (byte)data.readInt();
        System.out.println("frameCount:"+frameCount);
        frameData = new short[frameCount][];
        for (i = 0; i < frameCount; i++) {
          frameTileCount = (short)data.readInt();
//          System.out.println("frameTileCount:"+frameTileCount);
          collisionCount = (short)data.readInt();
          referencePointCount = (short)data.readInt();
          length = (short)(FRAME_HEADER_SIZE + frameTileCount * 4 +
              collisionCount * 4 +
              referencePointCount * 2);
          frameData[i] = new short[length];
          //struct
          //array[0] data length
          //array[1] tile count
          //arrat[2] collision count
          //arrat[3] reference count
          //arrat[4] frame width
          //arrat[5] frame height
          //...
          //array[6] tile index
          //array[7] tile x
          //array[8] tile y
          //array[9] tile transform
          //...
          //array[10] collision x
          //array[11] collision y
          //array[12] collision width
          //array[13] collision height
          //...
          //array[14] reference point x
          //array[15] reference point y
          //...

          frameData[i][0] = length;
          frameData[i][FRAME_TILE_COUNT_BIT] = frameTileCount;
          frameData[i][FRAME_COLLISION_COUNT_BIT] = collisionCount;
          frameData[i][FRAME_REFERENCE_POINT_COUNT_BIT] =
              referencePointCount;
          frameData[i][FRAME_TOP_POS_BIT] = data.readShort(); //frame top pos
          frameData[i][FRAME_BOTTOM_POS_BIT] = data.readShort(); //frame bottom pos
          frameData[i][FRAME_LEFT_POS_BIT] = data.readShort(); //frame left pos
          frameData[i][FRAME_RIGHT_POS_BIT] = data.readShort(); //frame right pos

          offset = FRAME_HEADER_SIZE;

          //read frame tile
          for (j = 0; j < frameTileCount; j++) {
            frameData[i][0 + offset] = data.readShort(); //tile index
            frameData[i][1 + offset] = data.readShort(); //tile x
            frameData[i][2 + offset] = data.readShort(); //tile y
            frameData[i][3 + offset] = data.readShort(); //tile transform
            offset += 4;
          }

          //read collision
          for (j = 0; j < collisionCount; j++) {
            frameData[i][0 + offset] = data.readShort(); //collision x
            frameData[i][1 + offset] = data.readShort(); //collision y
            frameData[i][2 + offset] = data.readShort(); //collision width
            frameData[i][3 + offset] = data.readShort(); //collision height
            offset += 4;
          }

          //reference point
          for (j = 0; j < referencePointCount; j++) {
            frameData[i][0 + offset] = data.readShort(); //reference point x
            frameData[i][1 + offset] = data.readShort(); //reference point y
            offset += 2;
          }
        }

        //read action
        actionCount = (byte)data.readInt();
//        System.out.println("actionCount:"+actionCount);
//        tileCount = data.readInt();
//        System.out.println("tileCount:"+tileCount);
//        if (tileMode == false) {
//          tileData = new int[tileCount][4];
//          //struct
//          //...
//          //array[0] tile x
//          //array[1] tile y
//          //array[2] tile width
//          //array[3] tile height
//          //...
//          for (i = 0; i < tileCount; i++) {
//        	  System.out.println("i:"+i);
//            tileData[i][0] = data.readShort();
//            tileData[i][1] = data.readShort();
//            tileData[i][2] = data.readShort();
//            tileData[i][3] = data.readShort();
//          }
//        }
//
//        //read frame
//        frameCount = data.readShort();
//        System.out.println("frameCount:"+frameCount);
        actionData = new short[actionCount][];

        for (i = 0; i < actionCount; i++) {
          sequenceLength = (byte)data.readInt();
//          System.out.println("sequenceLength:"+sequenceLength);
          delay = data.readByte();
//          System.out.println("delay:"+delay);
          if (delay == 1) {
            length = (short)(ACTION_HEADER_SIZE + sequenceLength * 2);
          } else {
            length = (short)(ACTION_HEADER_SIZE + sequenceLength);
          }
          actionData[i] = new short[length];
          //struct
          //array[0] data length
          //array[1] sequence count
          //array[2] action delay
          //array[3] action transform
          //...
          //array[4] action sequences
          //#array[5] delay time
          //...
          actionData[i][0] = length; //data length for c copy array
          actionData[i][ACTION_SEQUENCE_LENGTH_BIT] = sequenceLength; //sequence count
          actionData[i][ACTION_SEQUENCE_DELAY_BIT] = delay; //action delay
          actionData[i][ACTION_TRANSFORM_BIT] = (short)data.readInt(); //action transform
//          System.out.println("actionData[i][ACTION_TRANSFORM_BIT]:"+actionData[i][ACTION_TRANSFORM_BIT]);
          offset = ACTION_HEADER_SIZE;
          if (delay == 1) {
        	  for (j = 0; j < sequenceLength; j++) {
        		  //read sequence
        		  actionData[i][offset] = data.readShort();
        		  //read delay time
        		  actionData[i][offset + 1] = data.readShort();
        		  offset += 2;
        	  }
          } else {
        	  for (j = 0; j < sequenceLength; j++) {
        		  //read sequence
        		  actionData[i][offset] = data.readShort();
//        		  System.out.println("actionData[i][offset]:"+actionData[i][offset]);
        		  offset += 1;
        	  }
          }
        }
//      }
      //set image
      setImage(image);

      //load complete
      visible = true;

      //data.close();
      is.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public SpriteX(SpriteX spx) {
    //init vars
    int i, length;
    actionCount = spx.actionCount;
    frameCount = spx.frameCount;
    tileCount = spx.tileCount;
    actionData = new short[actionCount][];
    frameData = new short[frameCount][];
    tileData = new short[tileCount][];
    //copy action
    for (i = 0; i < actionCount; i++) {
      length = spx.actionData[i][0];
      actionData[i] = new short[length];
      System.arraycopy(spx.actionData[i], 0, actionData[i], 0, length);
    }
    //copy frame
    for (i = 0; i < frameCount; i++) {
      length = spx.frameData[i][0];
      frameData[i] = new short[length];
      System.arraycopy(spx.frameData[i], 0, frameData[i], 0, length);
    }
    //copy tile
    for (i = 0; i < tileCount; i++) {
      length = 4;
      tileData[i] = new short[length];
      System.arraycopy(spx.tileData[i], 0, tileData[i], 0, length);
    }
    //copy data
    actionIndex = spx.actionIndex;
    sequenceIndex = spx.sequenceIndex;
    image = spx.image;
    x = spx.x;
    y = spx.y;
    visible = spx.visible;
    lastTime = spx.lastTime;
    firstUpdate = spx.firstUpdate;
    disableUpdate = spx.disableUpdate;
  }

  public void enableUpdate(boolean enable) {
    if (disableUpdate && enable) {
      lastTime = System.currentTimeMillis();
    }
    disableUpdate = !enable;
  }

  public boolean isEnableUpdate() {
    return!disableUpdate;
  }

  public void setImage(Image image) {
//    if (image == null) {
//      throw new NullPointerException();
//    }
//    else {
    this.image = image;
//    }
  }

  public Image getImage() {
    return image;
  }

  public Image getImage(int index) {
    return tiles[index];
  }

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void move(int dx, int dy) {
    x += dx;
    y += dy;
  }

  public final int getX() {
    return x;
  }

  public final int getY() {
    return y;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setAction(byte actionIndex) {
    if (actionIndex < 0 || actionIndex >= actionCount) {
      throw new IndexOutOfBoundsException();
    } else {
      this.actionIndex = actionIndex;
      this.sequenceIndex = 0;
      this.firstUpdate = false;
    }
  }

  public int getAction() {
    return actionIndex;
  }

  public int getActionCount() {
    return actionCount;
  }

  public void setFrame(byte sequenceIndex) {
    if (sequenceIndex < 0 || sequenceIndex >= getSequenceLength()) {
      throw new IndexOutOfBoundsException();
    } else {
      this.sequenceIndex = sequenceIndex;
      this.firstUpdate = false;
    }
  }

  public int getFrame() {
    return sequenceIndex;
  }

  public void setTransform(byte transform) {
    if (transform < 0 || transform > 7) {
      throw new IllegalArgumentException();
    } else {
      actionData[actionIndex][ACTION_TRANSFORM_BIT] = transform;
    }
  }

  public int getTransform() {
    return actionData[actionIndex][ACTION_TRANSFORM_BIT];
  }

  public void deasilRotate90() {
    setTransform(ROTATE_TABLE[getTransform()][DEASIL_90]);
  }

  void anticlockwiseRotate90() {
    setTransform(ROTATE_TABLE[getTransform()][ANTICLOCKWISE_90]);
  }

  void horizontalMirror() {
    setTransform(ROTATE_TABLE[getTransform()][HORIZONTAL]);
  }

  void verticalMirror() {
    setTransform(ROTATE_TABLE[getTransform()][VERTICAL]);
  }

  public void nextFrame() {
    sequenceIndex = (byte)((sequenceIndex + 1) % getSequenceLength());
    firstUpdate = false;
  }

  public void prevFrame() {
    if (sequenceIndex == 0) {
      sequenceIndex = (byte)(getSequenceLength() - 1);
    } else {
      sequenceIndex--;
    }
    firstUpdate = false;
  }

  public void update(long time) {
    if (!isEnableUpdate()) {
      return;
    }
    if (isDelay()) {
      if (!firstUpdate) {
        firstUpdate = true;
        this.lastTime = time;
      }
      int dms = getDelayTime();
      if ( (time - this.lastTime) >= dms) {
        nextFrame();
        this.lastTime = time;
      }
    }
  }

  public void update() {
    if (!isEnableUpdate()) {
      return;
    }
    update(System.currentTimeMillis());
  }

  public int getSequenceFrame(int sequenceIndex) {
    if (isDelay()) {
      return actionData[actionIndex][ACTION_HEADER_SIZE +
          sequenceIndex * 2];
    } else {
      return actionData[actionIndex][ACTION_HEADER_SIZE + sequenceIndex];
    }
  }

  public int getSequenceFrame() {
    if (isDelay()) {
      return actionData[actionIndex][ACTION_HEADER_SIZE +
          sequenceIndex * 2];
    } else {
      return actionData[actionIndex][ACTION_HEADER_SIZE + sequenceIndex];
    }
  }

  public int getSequenceLength() {
    return actionData[actionIndex][ACTION_SEQUENCE_LENGTH_BIT];
  }

  public boolean isDelay() {
    if (actionData[actionIndex][ACTION_SEQUENCE_DELAY_BIT] == 1) {
      return true;
    } else {
      return false;
    }
  }

  public int getDelayTime() {
    return actionData[actionIndex][ACTION_HEADER_SIZE + sequenceIndex * 2 +
        1];
  }

  public int getReferencePointX(int index) {
    int frameIndex = getSequenceFrame();
    int frameTileCount = frameData[frameIndex][FRAME_TILE_COUNT_BIT];
    int frameCollisionCount = frameData[frameIndex][
        FRAME_COLLISION_COUNT_BIT];
    int offset = FRAME_HEADER_SIZE + frameTileCount * 4 +
        frameCollisionCount * 4 + index * 2;
    int refX = frameData[frameIndex][offset];
    int refY = frameData[frameIndex][offset + 1];
    return getTransformedReferenceX(refX, refY, getTransform()) + x;
  }

  public int getReferencePointY(int index) {
    int frameIndex = getSequenceFrame();
    int frameTileCount = frameData[frameIndex][FRAME_TILE_COUNT_BIT];
    int frameCollisionCount = frameData[frameIndex][
        FRAME_COLLISION_COUNT_BIT];
    int offset = FRAME_HEADER_SIZE + frameTileCount * 4 +
        frameCollisionCount * 4 + index * 2;
    int refX = frameData[frameIndex][offset];
    int refY = frameData[frameIndex][offset + 1];
    return getTransformedReferenceY(refX, refY, getTransform()) + y;
  }

  public int getReferencePointCount(int frameIndex) {
    if (frameIndex < 0 && frameIndex >= frameCount) {
      throw new ArrayIndexOutOfBoundsException();
    }
    int frameReferencePointCount = frameData[frameIndex][
        FRAME_REFERENCE_POINT_COUNT_BIT];
    return frameReferencePointCount;
  }

  public int getReferencePointCount() {
    int frameIndex = getSequenceFrame();
    return getReferencePointCount(frameIndex);
  }

  int getTransformedReferenceX(int x, int y, int transform) {
    int t_x = 0;
    switch (transform) {
    case TRANS_NONE:
      t_x = x;
      break;
    case TRANS_MIRROR:
      t_x = -x;
      break;
//      case TRANS_MIRROR_ROT180:
//        t_x = x;
//        break;
//      case TRANS_ROT90:
//        t_x = srcFrameHeight - y - 1;
//        break;
//      case TRANS_ROT180:
//        t_x = srcFrameWidth - x - 1;
//        break;
//      case TRANS_ROT270:
//        t_x = y;
//        break;
//      case TRANS_MIRROR_ROT90:
//        t_x = srcFrameHeight - y - 1;
//        break;
//      case TRANS_MIRROR_ROT270:
//        t_x = y;
//        break;
    }
    return t_x;
  }

  int getTransformedReferenceY(int x, int y, int transform) {
    int t_y = 0;
    switch (transform) {
    case TRANS_NONE:
      t_y = y;
      break;
    case TRANS_MIRROR:
      t_y = y;
      break;
//      case TRANS_MIRROR_ROT180:
//        t_y = srcFrameHeight - y - 1;
//        break;
//      case TRANS_ROT90:
//        t_y = x;
//        break;
//      case TRANS_ROT180:
//        t_y = srcFrameHeight - y - 1;
//        break;
//      case TRANS_ROT270:
//        t_y = srcFrameWidth - x - 1;
//        break;
//      case TRANS_MIRROR_ROT90:
//        t_y = srcFrameWidth - x - 1;
//        break;
//      case TRANS_MIRROR_ROT270:
//        t_y = x;
//        break;
    }
    return t_y;
  }

  public int getCollidesX(int frame, int index) {
    if (index < 0 || index >= frameData[frame][FRAME_COLLISION_COUNT_BIT]) {
      throw new ArrayIndexOutOfBoundsException();
    } else {
      int offset = FRAME_HEADER_SIZE +
          frameData[frame][FRAME_TILE_COUNT_BIT] * 4 + index * 4;

      return frameData[frame][offset] + x;
    }
  }

  public int getCollidesY(int frame, int index) {
    if (index < 0 || index >= frameData[frame][FRAME_COLLISION_COUNT_BIT]) {
      throw new ArrayIndexOutOfBoundsException();
    } else {
      int offset = FRAME_HEADER_SIZE +
          frameData[frame][FRAME_TILE_COUNT_BIT] * 4 + index * 4 +
          1;

      return frameData[frame][offset] + y;
    }
  }

  public int getCollidesWidth(int frame, int index) {
    if (index < 0 || index >= frameData[frame][FRAME_COLLISION_COUNT_BIT]) {
      throw new ArrayIndexOutOfBoundsException();
    } else {
      int offset = FRAME_HEADER_SIZE +
          frameData[frame][FRAME_TILE_COUNT_BIT] * 4 + index * 4 +
          2;

      return frameData[frame][offset];
    }
  }

  public int getCollidesHeight(int frame, int index) {
    if (index < 0 || index >= frameData[frame][FRAME_COLLISION_COUNT_BIT]) {
      throw new ArrayIndexOutOfBoundsException();
    } else {
      int offset = FRAME_HEADER_SIZE +
          frameData[frame][FRAME_TILE_COUNT_BIT] * 4 + index * 4 +
          3;

      return frameData[frame][offset];
    }
  }

  public int getCollidesX(int index) {
    int frameIndex = getSequenceFrame();
    return getCollidesX(frameIndex, index);
  }

  public int getCollidesY(int index) {
    int frameIndex = getSequenceFrame();
    return getCollidesY(frameIndex, index);
  }

  public int getCollidesWidth(int index) {
    int frameIndex = getSequenceFrame();
    return getCollidesWidth(frameIndex, index);
  }

  public int getCollidesHeight(int index) {
    int frameIndex = getSequenceFrame();
    return getCollidesHeight(frameIndex, index);
  }

  public int getCollidesCount(int index) {
    return frameData[index][FRAME_COLLISION_COUNT_BIT];
  }

  public int getCollidesCount() {
    return frameData[getSequenceFrame()][FRAME_COLLISION_COUNT_BIT];
  }

  public boolean collidesWith(SpriteX spx, int spxCollides, int thisCollides,
                              int type) {
    if (!spx.visible || !this.visible) {
      return false;
    }

    int x = spx.getCollidesX(spxCollides);
    int y = spx.getCollidesY(spxCollides);
    int width = spx.getCollidesWidth(spxCollides);
    int height = spx.getCollidesHeight(spxCollides);

    return collidesWith(x, y, width, height, thisCollides, type);
  }

  public boolean collidesWith(Image image, int x, int y, int collides,
                              int type) {
    return collidesWith(x, y, image.getWidth(), image.getHeight(), collides,
                        type);
  }

  public boolean collidesWith(int x, int y, int width, int height,
                              int collides,
                              int type) {
    if (!this.visible) {
      return false;
    }

    int x1 = this.getCollidesX(collides);
    int y1 = this.getCollidesY(collides);
    int w1 = this.getCollidesWidth(collides);
    int h1 = this.getCollidesHeight(collides);

    if (type == COLLISION_INTERSECT) {
      return intersectRect(x1, y1, w1, h1, x, y, width, height);
    } else if (type == COLLISION_INCLUSION) {
      return inclusionRect(x1, y1, w1, h1, x, y, width, height);
    } else {
      throw new ArithmeticException();
    }
  }

  public boolean collidesWith(SpriteX spx) {
    if (!spx.visible || !this.visible) {
      return false;
    }
    if (this.getCollidesCount() == 0 || spx.getCollidesCount() == 0) {
      return false;
    }
    return collidesWith(spx, 0, 0, COLLISION_INTERSECT);
  }

  public boolean collidesWith(Image image, int x, int y) {
    if (!this.visible) {
      return false;
    }
    if (this.getCollidesCount() == 0) {
      return false;
    }
    return collidesWith(image, x, y, 0, COLLISION_INTERSECT);
  }

  public boolean collidesWith(int x, int y, int width, int height) {
    if (!this.visible) {
      return false;
    }
    if (this.getCollidesCount() == 0) {
      return false;
    }
    return collidesWith(x, y, width, height, 0, COLLISION_INTERSECT);
  }

  public static boolean intersectRect(int x1, int y1, int width1, int height1,
                                      int x2, int y2, int width2, int height2) {
    if (y2 + height2 < y1 || y2 > y1 + height1 || x2 + width2 < x1 ||
        x2 > x1 + width1) {
      return false;
    } else {
      return true;
    }
  }

  public static boolean inclusionRect(int x1, int y1, int width1, int height1,
                                      int x2, int y2, int width2, int height2) {
    if (y2 >= y1 &&
        y2 + height2 <= y1 + height1 &&
        x2 >= x1 &&
        x2 + width2 <= x1 + width1) {
      return true;
    }

    return false;
  }

  public void originOffset(int x, int y) {
    originOffsetX = x;
    originOffsetY = y;
  }

  public int getFrameTopPos() {
    return frameData[getSequenceFrame()][FRAME_TOP_POS_BIT] + y;
  }

  public int getFrameBottomPos() {
    return frameData[getSequenceFrame()][FRAME_BOTTOM_POS_BIT] + y;
  }

  public int getFrameLeftPos() {
    return frameData[getSequenceFrame()][FRAME_LEFT_POS_BIT] + x;
  }

  public int getFrameRightPos() {
    return frameData[getSequenceFrame()][FRAME_RIGHT_POS_BIT] + x;
  }

  public int getFrameWidth() {
    return getFrameRightPos() - getFrameLeftPos();
  }

  public int getFrameHeight() {
    return getFrameBottomPos() - getFrameTopPos();
  }

  public void paint(Graphics g) {
    paint(g,x,y);
  }

  public void paint(Graphics g, int x, int y) {
    if (g == null) {
      throw new NullPointerException();
    }
    if (visible) {
      x -= originOffsetX;
      y -= originOffsetY;

      int clipX = g.getClipX();
      int clipY = g.getClipY();
      int clipWidth = g.getClipWidth();
      int clipHeight = g.getClipHeight();

      int actionTransform = getTransform();
      if (actionTransform == this.TRANS_NONE) {
        int tileIndex; //tile index
        int dx; //tile dest x
        int dy; //tile dest y
        int transform; //tile transform
        int tx; //tile x
        int ty; //tile y
        int tw; //tile width
        int th; //tile height
        int frameIndex = getSequenceFrame();
        int tileCount = frameData[frameIndex][FRAME_TILE_COUNT_BIT];
        int offset = FRAME_HEADER_SIZE; //tile data offset
        for (int i = 0; i < tileCount; i++) {
          if (tileMode) {
            tileIndex = frameData[frameIndex][0 + offset];
            dx = frameData[frameIndex][1 + offset] + x;
            dy = frameData[frameIndex][2 + offset] + y;
            transform = frameData[frameIndex][3 + offset];
            tx = 0;
            ty = 0;
            tw = tiles[tileIndex].getWidth();
            th = tiles[tileIndex].getHeight();

            if (intersectRect(dx, dy, tw, th, clipX, clipY, clipWidth,
                              clipHeight)) {
              drawRegion(g, tiles[tileIndex], tx, ty, tw, th, transform, dx, dy,
                         Graphics.TOP | Graphics.LEFT);
            }
          } else {
            tileIndex = frameData[frameIndex][0 + offset];
            dx = frameData[frameIndex][1 + offset] + x;
            dy = frameData[frameIndex][2 + offset] + y;
            transform = frameData[frameIndex][3 + offset];
            tx = tileData[tileIndex][0];
            ty = tileData[tileIndex][1];
            tw = tileData[tileIndex][2];
            th = tileData[tileIndex][3];

            if (intersectRect(dx, dy, tw, th, clipX, clipY, clipWidth,
                              clipHeight)) {
              drawRegion(g, image, tx, ty, tw, th, transform, dx, dy,
                         Graphics.TOP | Graphics.LEFT);
            }
          }
          offset += 4;
        }
      } else {
        //render transform action
        int tileIndex; //tile index
        int dx; //tile dest x
        int dy; //tile dest y
        int transform; //tile transform
        int tx; //tile x
        int ty; //tile y
        int tw; //tile width
        int th; //tile height
        int cx;
        int cy;
        int frameIndex = getSequenceFrame();
        int tileCount = frameData[frameIndex][FRAME_TILE_COUNT_BIT];
        int offset = FRAME_HEADER_SIZE; //tile data offset
        for (int i = 0; i < tileCount; i++) {
          if (tileMode) {
            tileIndex = frameData[frameIndex][0 + offset];
            dx = frameData[frameIndex][1 + offset] + x;
            dy = frameData[frameIndex][2 + offset] + y;
            transform = frameData[frameIndex][3 + offset];
            tx = 0;
            ty = 0;
            tw = tiles[tileIndex].getWidth();
            th = tiles[tileIndex].getHeight();

            cx = x - dx;
            cy = y - dy;
            //finally transform
            transform = TRANSFORM_TABLE[transform][actionTransform];
            //adjust coordinate
            switch (actionTransform) {
            case TRANS_NONE:

              //null
              break;
            case TRANS_ROT90:

              dx = x - (th - cy);
              dy = y - cx;
              break;
            case TRANS_ROT180:

              dx = x - (tw - cx);
              dy = y - (th - cy);
              break;
            case TRANS_ROT270:

              dx = x - cy;
              dy = y - (tw - cx);
              break;
            case TRANS_MIRROR:

              dx = x - (tw - cx);
              break;
            case TRANS_MIRROR_ROT90:

              dx = x - (th - cy);
              dy = y - (tw - cx);
              break;
            case TRANS_MIRROR_ROT180:

              dy = y - (th - cy);
              break;
            case TRANS_MIRROR_ROT270:

              dx = x - cy;
              dy = y - cx;
            break;
            }
            if (intersectRect(dx, dy, tw, th, clipX, clipY, clipWidth,
                              clipHeight)) {
              drawRegion(g, tiles[tileIndex], tx, ty, tw, th, transform, dx, dy,
                         Graphics.TOP | Graphics.LEFT);
            }
            offset += 4;
          } else {
            tileIndex = frameData[frameIndex][0 + offset];
            dx = frameData[frameIndex][1 + offset] + x;
            dy = frameData[frameIndex][2 + offset] + y;
            transform = frameData[frameIndex][3 + offset];
            tx = tileData[tileIndex][0];
            ty = tileData[tileIndex][1];
            tw = tileData[tileIndex][2];
            th = tileData[tileIndex][3];

            cx = x - dx;
            cy = y - dy;
            //finally transform
            transform = TRANSFORM_TABLE[transform][actionTransform];
            //adjust coordinate
            switch (actionTransform) {
            case TRANS_NONE:

              //null
              break;
            case TRANS_ROT90:

              dx = x - (th - cy);
              dy = y - cx;
              break;
            case TRANS_ROT180:

              dx = x - (tw - cx);
              dy = y - (th - cy);
              break;
            case TRANS_ROT270:

              dx = x - cy;
              dy = y - (tw - cx);
              break;
            case TRANS_MIRROR:

              dx = x - (tw - cx);
              break;
            case TRANS_MIRROR_ROT90:

              dx = x - (th - cy);
              dy = y - (tw - cx);
              break;
            case TRANS_MIRROR_ROT180:

              dy = y - (th - cy);
              break;
            case TRANS_MIRROR_ROT270:

              dx = x - cy;
              dy = y - cx;
            break;
            }
            if (intersectRect(dx, dy, tw, th, clipX, clipY, clipWidth,
                              clipHeight)) {
              drawRegion(g, image, tx, ty, tw, th, transform, dx, dy,
                         Graphics.TOP | Graphics.LEFT);
            }
            offset += 4;
          }
        }
      }
    }
  }

  public static void drawRegionMIDP1(Graphics g, Image src, int x_src,
                                     int y_src,
                                     int width, int height, int transform,
                                     int x_dest,
                                     int y_dest, int anchor) {
    //insure object not null
    if (g == null || src == null) {
      throw new NullPointerException();
    }
    //check anchor validity
    if (!checkAnchor(anchor, Graphics.BASELINE)) {
      throw new IllegalArgumentException();
    }
    //check transform validity
    if (transform < 0 || transform > 7) {
      throw new IllegalArgumentException();
    }
    //insure clip rect in image bound
    if (width < 0 || height < 0 || x_src < 0 || y_src < 0 ||
        x_src + width > src.getWidth() || y_src + height > src.getHeight()) {
      throw new IllegalArgumentException();
    }
    //save graphics old clip bound
    int clipX = g.getClipX();
    int clipY = g.getClipY();
    int clipWidth = g.getClipWidth();
    int clipHeight = g.getClipHeight();

    //if inverted axes width and height is change for
    if ( (INVERTED_AXES & transform) != 0) {
      if (anchor != 0) {
        //bottom
        if ( (anchor & g.BOTTOM) != 0) {
          y_dest -= width;
        }
        //right
        if ( (anchor & g.RIGHT) != 0) {
          x_dest -= height;
        }
        //horizontal
        if ( (anchor & g.HCENTER) != 0) {
          x_dest -= height / 2;
        }
        //vertical
        if ( (anchor & g.VCENTER) != 0) {
          y_dest -= width / 2;
        }
      }
      //clip
      g.clipRect(x_dest, y_dest, height, width);
    } else {
      if (anchor != 0) {
        //bottom
        if ( (anchor & g.BOTTOM) != 0) {
          y_dest -= height;
        }
        //right
        if ( (anchor & g.RIGHT) != 0) {
          x_dest -= width;
        }
        //horizontal
        if ( (anchor & g.HCENTER) != 0) {
          x_dest -= width / 2;
        }
        //vertical
        if ( (anchor & g.VCENTER) != 0) {
          y_dest -= height / 2;
        }
      }
      //clip
      g.clipRect(x_dest, y_dest, width, height);
    }

    //x offset
    int x_offset = 0;
    //y offset
    int y_offset = 0;

    //image width
    int srcWidth = src.getWidth();
    //image height
    int srcHeight = src.getHeight();

    switch (transform) {
    case TRANS_NONE:
      g.drawImage(src, x_dest - x_src, y_dest - y_src, g.LEFT | g.TOP);
      g.setClip(clipX, clipY, clipWidth, clipHeight);
      return;
    case TRANS_ROT90:
      x_offset = srcHeight - (y_src + height);
      y_offset = x_src;
      break;
    case TRANS_ROT180:
      x_offset = srcWidth - (x_src + width);
      y_offset = srcHeight - (y_src + height);
      break;
    case TRANS_ROT270:
      x_offset = y_src;
      y_offset = srcWidth - (x_src + width);
      break;
    case TRANS_MIRROR:
      x_offset = srcWidth - (x_src + width);
      y_offset = y_src;
      break;
    case TRANS_MIRROR_ROT90:
      x_offset = srcHeight - (y_src + height);
      y_offset = srcWidth - (x_src + width);
      break;
    case TRANS_MIRROR_ROT180:
      x_offset = x_src;
      y_offset = srcHeight - (y_src + height);
      break;
    case TRANS_MIRROR_ROT270:
      x_offset = y_src;
      y_offset = x_src;
    break;
    }

    x_dest -= x_offset;
    y_dest -= y_offset;

    int clipX1 = g.getClipX();
    int clipY1 = g.getClipY();
    int clipX2 = g.getClipX() + g.getClipWidth();
    int clipY2 = g.getClipY() + g.getClipHeight();

    if ( (INVERTED_AXES & transform) != 0) {
      int temp = srcWidth;
      srcWidth = srcHeight;
      srcHeight = temp;
    }

    int src_start_x = Math.max(0, clipX1 - x_dest);
    int src_start_y = Math.max(0, clipY1 - y_dest);

    int dest_start_x = Math.max(clipX1, x_dest);
    int dest_start_y = Math.max(clipY1, y_dest);

    int dest_right = x_dest + srcWidth;
    int dest_bottom = y_dest + srcHeight;

    int copy_width = srcWidth - src_start_x;
    int copy_height = srcHeight - src_start_y;

    copy_width -= Math.max(0, dest_right - clipX2);
    copy_height -= Math.max(0, dest_bottom - clipY2);

    int px = 0;
    int py = 0;

    for (int i = 0; i < copy_height; i++) {
      for (int j = 0; j < copy_width; j++) {
        switch (transform) {
        case TRANS_ROT90:
          px = src_start_y + i;
          py = (srcWidth - 1 - j) - src_start_x;
          break;
        case TRANS_ROT180:
          px = (srcWidth - 1 - j) - src_start_x;
          py = (srcHeight - 1 - i) - src_start_y;
          break;
        case TRANS_ROT270:
          px = (srcHeight - 1 - i) - src_start_y;
          py = src_start_x + j;
          break;
        case TRANS_MIRROR:
          px = (srcWidth - 1 - j) - src_start_x;
          py = src_start_y + i;
          break;
        case TRANS_MIRROR_ROT90:
          px = (srcHeight - 1 - i) - src_start_y;
          py = (srcWidth - 1 - j) - src_start_x;
          break;
        case TRANS_MIRROR_ROT180:
          px = src_start_x + j;
          py = (srcHeight - 1 - i) - src_start_y;
          break;
        case TRANS_MIRROR_ROT270:
          px = src_start_y + i;
          py = src_start_x + j;
        break;
        }

        g.setClip(dest_start_x + j, dest_start_y + i, 1, 1);
        g.drawImage(src, dest_start_x + j - px, dest_start_y + i - py,
                    g.LEFT | g.TOP);
      }
    }

    g.setClip(clipX, clipY, clipWidth, clipHeight);
  }
/*
    public static void drawRegionNokia(KGraphics g, KImage src, int x_src,
                                       int y_src,
                                       int width, int height, int transform,
                                       int x_dest,
                                       int y_dest, int anchor)
    {
        //insure object not null
        if(g == null || src == null)
        {
            throw new NullPointerException();
        }
        //check anchor validity
        if(!checkAnchor(anchor, KGraphics.BASELINE))
        {
            throw new IllegalArgumentException();
        }
        //check transform validity
        if(transform < 0 || transform > 7)
        {
           throw new IllegalArgumentException();
        }
        //insure clip rect in image bound
        if(width < 0 || height < 0 || x_src < 0 || y_src < 0 ||
           x_src + width > src.getWidth() || y_src + height > src.getHeight())
        {
            throw new IllegalArgumentException();
        }
        //save graphics old clip bound
        int clipX = g.getClipX();
        int clipY = g.getClipY();
        int clipWidth = g.getClipWidth();
        int clipHeight = g.getClipHeight();

        //if inverted axes width and height is change for
        if((INVERTED_AXES & transform) != 0)
        {
            if(anchor != 0)
            {
                //bottom
                if((anchor & g.BOTTOM) != 0)
                {
                    y_dest -= width;
                }
                //right
                if((anchor & g.RIGHT) != 0)
                {
                   x_dest -= height;
                }
                //horizontal
                if((anchor & g.HCENTER) != 0)
                {
                   x_dest -= height / 2;
                }
               //vertical
                if((anchor & g.VCENTER) != 0)
                {
                    y_dest -= width / 2;
                }
            }
            //clip
            g.clipRect(x_dest, y_dest, height, width);
        }
       else
        {
            if(anchor != 0)
            {
                //bottom
                if((anchor & g.BOTTOM) != 0)
                {
                    y_dest -= height;
                }
                //right
                if((anchor & g.RIGHT) != 0)
                {
                    x_dest -= width;
                }
                //horizontal
                if((anchor & g.HCENTER) != 0)
                {
                    x_dest -= width / 2;
                }
                //vertical
                if((anchor & g.VCENTER) != 0)
                {
                    y_dest -= height / 2;
                }
            }
            //clip
            g.clipRect(x_dest, y_dest, width, height);
        }

       //nokia transform value
        int manipulation = 0;
        //x offset
        int x_offset = 0;
        //y offset
        int y_offset = 0;

        switch(transform)
        {
            case TRANS_NONE:

                //transform is none use KGraphics#drawKImage
                g.drawKImage(src, x_dest - x_src, y_dest - y_src, 0);

                //resume graphics old clip
                g.setClip(clipX, clipY, clipWidth, clipHeight);

                //return
                return;
            case TRANS_ROT90:
                manipulation = com.nokia.mid.ui.DirectGraphics.ROTATE_270;
                x_offset = src.getHeight() - (y_src + height);
                y_offset = x_src;
                break;
            case TRANS_ROT180:
                manipulation = com.nokia.mid.ui.DirecKGraphics.ROTATE_180;
                x_offset = src.getWidth() - (x_src + width);
                y_offset = src.getHeight() - (y_src + height);
                break;
            case TRANS_ROT270:
                manipulation = com.nokia.mid.ui.DirectGraphics.ROTATE_90;
                x_offset = y_src;
                y_offset = src.getWidth() - (x_src + width);
                break;            case TRANS_MIRROR:
               manipulation = com.nokia.mid.ui.DirectGraphics.FLIP_HORIZONTAL;
               x_offset = src.getWidth() - (x_src + width);
               y_offset = y_src;
                break;
            case TRANS_MIRROR_ROT90:
                manipulation = com.nokia.mid.ui.DirectGraphics.FLIP_HORIZONTAL |
                               com.nokia.mid.ui.DirectGraphics.ROTATE_90;
                x_offset = src.getHeight() - (y_src + height);
                y_offset = src.getWidth() - (x_src + width);
                break;
            case TRANS_MIRROR_ROT180:
                manipulation = com.nokia.mid.ui.DirectGraphics.FLIP_HORIZONTAL |
                               com.nokia.mid.ui.DirectGraphics.ROTATE_180;
                x_offset = x_src;
                y_offset = src.getHeight() - (y_src + height);
                break;
            case TRANS_MIRROR_ROT270:
                manipulation = com.nokia.mid.ui.DirectGraphics.FLIP_HORIZONTAL |
                               com.nokia.mid.ui.DirectGraphics.ROTATE_270;
                x_offset = y_src;
                y_offset = x_src;
                break;
       }
        //draw transform image
        com.nokia.mid.ui.DirectGraphics dg = com.nokia.mid.ui.DirectUtils.getDirectGraphics(g);
        dg.drawKImage(src, x_dest - x_offset, y_dest - y_offset, 0,
                      manipulation);
        //resume graphics old clip
        g.setClip(clipX, clipY, clipWidth, clipHeight);
    }
*/
  public static boolean checkAnchor(int anchor, int illegal_vpos) {
    boolean right;

    if (anchor == 0) {
      return true; /* special case: 0 is ok */
    }

    right = (anchor > 0) && (anchor < (Graphics.BASELINE << 1))
        && ( (anchor & illegal_vpos) == 0);

    if (right) {
      int n = anchor &
          (Graphics.TOP | Graphics.BOTTOM | Graphics.BASELINE |
           Graphics.VCENTER);
      right = (n != 0) && ( (n & (n - 1)) == 0); /* exactly one bit set */
    }

    if (right) {
      int n = anchor &
          (Graphics.LEFT | Graphics.RIGHT | Graphics.HCENTER);
      right = (n != 0) && ( (n & (n - 1)) == 0); /* exactly one bit set */
    }

    return right;
  }

  public static void drawRegion(Graphics g, Image src, int x_src, int y_src,
                                int width, int height, int transform,
                                int x_dest,
                                int y_dest, int anchor) {
    //MIDP2.0
    g.drawRegion(src, x_src, y_src, width, height, transform, x_dest,
                 y_dest,
                 anchor);
//    //MIDP1.0
//    drawRegionMIDP1(g, src, x_src, y_src, width, height, transform, x_dest,
//                    y_dest,
//                    anchor);
    //MIDP nokia
//    drawRegionNokia(g, src, x_src, y_src, width, height, transform, x_dest,
//                    y_dest, anchor);
  }

}
