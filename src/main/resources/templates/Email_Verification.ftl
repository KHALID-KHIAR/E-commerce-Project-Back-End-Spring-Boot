<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        .container{
            width: 75%;
            margin: auto;
            padding: 10px;
            background-color: whitesmoke;

        }
        .header {
            background-color:black           ;   /* linear-gradient(75deg,#fad976,#e7744e); */
            height: 80px;
            width: auto;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .header-img{
            height: 90px;
            background-color: white;
            width: auto;
            display: flex;
            align-items: center;
            padding: 20px;
            justify-content: center;
            border-radius: 50%;
            overflow: hidden;
        }
        .header .header-img img {
            width: fit-content;
            height: 90%;

        }

        .main{
            padding: 5px;
            background-color: white;
            height: 300px;
        }
        .footer{
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: lightslategrey;
            font-size: small;
            margin: 35px auto;
        }
        .footer p {
            margin: 0px ;
        }
        @media only screen and (max-width: 600px) {
            .container {
                width: auto;
            }
        }

    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="header-img" style="display: block;margin: auto;border-radius: 50%;height: 90px;background-color: white;width: auto;padding: 20px;">
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5_hsJRbjXaubYeg-jMSIovjWleam_9zFgOA&usqp=CAU"
                 alt="Me Logo">
        </div>
    </div>
    <div class="main" style="padding: 5px;background-color: white;height: auto;">




        <p>Hi ${firstname},</p>
        <p>Thank you for registering with our website. <br>To complete your registration, please Verify you Email address by clicking on the link:</p>
        <p>Link : <strong> ${link} </strong></p><br>
        <p>If you did not register with our website, please ignore this email.</p>

        <p>If you have any questions or need further assistance, please don't hesitate to contact us.</p>

    </div>
        <div class="footer" style="display: block;flex-direction: column;align-items: center;justify-content: center;color: lightslategrey;font-size: small;margin: 35px auto;">
            <p>ZETSGO <br>AI & Digital solutions</p>
            <p>www.kh-khalid.site</p>
            <p> 1 avenue Hassan II , 8 ème étage, Casablanca, Morocco</p>
            <p>Mob : +212 667360053</p>
            <p>Contact: contact@kh-khalid.com</p>
        </div>
</div>
</body>
</html>