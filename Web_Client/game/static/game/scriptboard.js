let inaccesiblePressed = false;
let waitPressed = false;
let placePiecesPressed = false;

let inaccessibleX = [];
let inaccessibleY = [];
let inaccesibleCount = 0;

let waitX = [];
let waitY = [];
let waitCount = 0;

let piecesX = [];
let piecesY = [];
let pieces = [];
let piecesCount = 0;
let piece = undefined;
let piecesImages = [];

let height = undefined;
let width = undefined;
let a = undefined;

let canvas = document.getElementById("canvas-board");
let context = canvas.getContext("2d");



document.getElementById("piecesDD").style.visibility = "hidden";

let counter = 0;

for (let i = 0; i < 12; i++) {
  piecesImages[i] = new Image();
  piecesImages[i].src = "{% static 'game/temp/chessPieces/" + i + ".png' %}";
}

/*
WK = 0
WQ = 1
WR = 2
WB = 3
WN = 4
WP = 5
BK = 6
BQ = 7
BR = 8
BB = 9
BN = 10
BP = 11
*/

function drawBoard() {
  document.getElementById("piecesDD").style.visibility = "hidden";

  inaccessibleX = [];
  inaccessibleY = [];
  inaccesibleCount = 0;

  waitX = [];
  waitY = [];
  waitCount = 0;

  piecesX = [];
  piecesY = [];
  pieces = [];
  piecesCount = 0;

  height = document.getElementById("height").value;
  width = document.getElementById("width").value;

  if ((screen.width / width) > (screen.height / height)) {
    a = (screen.height / 1.1) / height;
  } else {
    a = (screen.width / 1.1) / width;
  }

  canvas.width = width * a;
  canvas.height = height * a;

  context.clearRect(0, 0, canvas.width, canvas.height);

  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      if ((x + y) % 2 == 0) {
        context.fillStyle = "black";
      } else {
        context.fillStyle = "white";
      }
      context.fillRect(x * a, y * a, a, a);
    }
  }
}

function getPosition(e) {
  let cursorX = Math.floor((e.clientX - document.getElementById("canvas-board").offsetLeft) / a);
  let cursorY = Math.floor((e.clientY - document.getElementById("canvas-board").offsetTop) / a);

  for (let i = 0; i < inaccesibleCount; i++) {
    if ((cursorX == inaccessibleX[i]) && (cursorY == inaccessibleY[i])) {
      counter++;
    }
  }
  for (let i = 0; i < waitCount; i++) {
    if ((cursorX == waitX[i]) && (cursorY == waitY[i])) {
      counter++;
    }
  }
  for (let i = 0; i < piecesCount; i++) {
    if ((cursorX == piecesX[i]) && (cursorY == piecesY[i])) {
      counter++;
    }
  }
  if (counter == 0) {
    if (inaccesiblePressed == true) {
      setInaccesible(cursorX, cursorY);
    }
    if (waitPressed == true) {
      setWait(cursorX, cursorY);
    }
    if (placePiecesPressed == true) {
      placePieces(cursorX, cursorY);
    }
  }
  counter = 0;
}

function placePieces(CursorX, CursorY) {
  piecesX[piecesCount] = CursorX;
  piecesY[piecesCount] = CursorY;
  pieces[piecesCount] = piece;
  piecesCount++;

  console.log(piecesX, piecesY, pieces, piecesCount, piece);

  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      for (let i = 0; i < piecesCount; i++) {
        cursorX = piecesX[i];
        cursorY = piecesY[i];
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "K")) {
          piecesImages[0].onload = function() {
          context.drawImage(piecesImages[0], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "Q")) {
          piecesImages[1].onload = function() {
          context.drawImage(piecesImages[1], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "R")) {
          piecesImages[2].onload = function() {
          context.drawImage(piecesImages[2], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "B")) {
          piecesImages[3].onload = function() {
          context.drawImage(piecesImages[3], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "N")) {
          piecesImages[4].onload = function() {
          context.drawImage(piecesImages[4], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "P")) {
          piecesImages[5].onload = function() {
          context.drawImage(piecesImages[5], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "k")) {
          piecesImages[6].onload = function() {
          context.drawImage(piecesImages[6], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "q")) {
          piecesImages[7].onload = function() {
          context.drawImage(piecesImages[7], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "r")) {
          piecesImages[8].onload = function() {
          context.drawImage(piecesImages[8], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "b")) {
          piecesImages[9].onload = function() {
          context.drawImage(piecesImages[9], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "n")) {
          piecesImages[10].onload = function() {
          context.drawImage(piecesImages[10], x * a, y *a, a, a);
          }
        }
        if ((x == cursorX) && (y == cursorY) && (pieces[i] == "p")) {
          piecesImages[11].onload = function() {
          context.drawImage(piecesImages[11], x * a, y *a, a, a);
          }
        }
      }
    }
  }
}


function setWait(CursorX, CursorY) {
  waitX[waitCount] = CursorX;
  waitY[waitCount] = CursorY;
  waitCount++;

  console.log(waitX, waitY, waitCount);

  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      for (let i = 0; i < waitCount; i++) {
        cursorX = waitX[i];
        cursorY = waitY[i];
        if ((x == cursorX) && (y == cursorY)) {
          context.fillStyle = "red";
          context.fillRect(x * a, y * a, a, a);
        }
      }
    }
  }
}

function setInaccesible(CursorX, CursorY) {

  inaccessibleX[inaccesibleCount] = CursorX;
  inaccessibleY[inaccesibleCount] = CursorY;
  inaccesibleCount++;

  console.log(inaccessibleX, inaccessibleY, inaccesibleCount);

  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      for (let i = 0; i < inaccesibleCount; i++) {
        cursorX = inaccessibleX[i];
        cursorY = inaccessibleY[i];
        if ((x == cursorX) && (y == cursorY)) {
          context.fillStyle = "grey";
          context.fillRect(x * a, y * a, a, a);
        }
      }
    }
  }
}

function inaccesibleButton() {
  inaccesiblePressed = true;
  waitPressed = false;
  placePiecesPressed = false;
  console.log("inaccesible");
  document.getElementById("piecesDD").style.visibility = "hidden";
}

function waitButton() {
  inaccesiblePressed = false;
  waitPressed = true;
  placePiecesPressed = false;
  console.log("wait");
  document.getElementById("piecesDD").style.visibility = "hidden";
}

function piecesButton() {
  inaccesiblePressed = false;
  waitPressed = false;
  placePiecesPressed = true;
  console.log("place");
  document.getElementById("piecesDD").style.visibility = "visible";
}
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

document.getElementById("btn-generate").addEventListener('click', drawBoard);
document.getElementById("canvas-board").addEventListener('click', getPosition);
document.getElementById("btn-inaccessible").addEventListener('click', inaccesibleButton);
document.getElementById("btn-wait").addEventListener('click', waitButton);
document.getElementById("btn-place").addEventListener('click', piecesButton);

document.getElementById("WK").addEventListener('click', function () { console.log("WK"); piece = "K"; });
document.getElementById("WQ").addEventListener('click', function () { console.log("WQ"); piece = "Q"; });
document.getElementById("WR").addEventListener('click', function () { console.log("WR"); piece = "R"; });
document.getElementById("WB").addEventListener('click', function () { console.log("WB"); piece = "B"; });
document.getElementById("WN").addEventListener('click', function () { console.log("WN"); piece = "N"; });
document.getElementById("WP").addEventListener('click', function () { console.log("WP"); piece = "P"; });
document.getElementById("BK").addEventListener('click', function () { console.log("BK"); piece = "k"; });
document.getElementById("BQ").addEventListener('click', function () { console.log("BQ"); piece = "q"; });
document.getElementById("BR").addEventListener('click', function () { console.log("BR"); piece = "r"; });
document.getElementById("BB").addEventListener('click', function () { console.log("BB"); piece = "b"; });
document.getElementById("BN").addEventListener('click', function () { console.log("BN"); piece = "n"; });
document.getElementById("BP").addEventListener('click', function () { console.log("BP"); piece = "p"; });
//
//function, that copnverts the locations of the pieces to a string of FEN notation and include even the special squares
function convertToFEN() {
  let fen = "";
  let empty = 0;
  for (let y = 0; y < height; y++) {
    empty = 0;
    for (let x = 0; x < width; x++) {
      for (let i = 0; i < pieces.length; i++) {
        if ((x == piecesX[i]) && (y == piecesY[i])) {
          if (empty != 0) {
            fen += empty;
            empty = 0;
          }
          fen += pieces[i];
        }
      }
      for (let i = 0; i < waitCount; i++) {
        if ((x == waitX[i]) && (y == waitY[i])) {
          if (empty != 0) {
            fen += empty;
            empty = 0;
          }
          fen += "w";
        }
      }
      for (let i = 0; i < inaccesibleCount; i++) {
        if ((x == inaccessibleX[i]) && (y == inaccessibleY[i])) {
          if (empty != 0) {
            fen += empty;
            empty = 0;
          }
          fen += "x";
        }
      }
      if (empty != 0) {
        fen += empty;
        empty = 0;
      }
      if (x != width - 1) {
        fen += "/";
      }
    }
    if (y != height - 1) {
      fen += "-";
    }
  }
  var data = {
    fen: fen
  };
  
makeAjaxCall('mongodb+srv://UwU:MamRadTuhleDatabazi69@customchess.hmtwp1r.mongodb.net/?retryWrites=true&w=majority', data);
}

async function makeAjaxCall(url, data) {
  let responseData;
  await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.json())
    .then((data) => (responseData = data))
    .catch((error) => console.log(error));
  return responseData;
}

document.getElementById("btn-finish").addEventListener('click', convertToFEN);
