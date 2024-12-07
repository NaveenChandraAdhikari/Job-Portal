package com.jobportal.Job.Portal.entity;

public class Data {

    public static String getMessageBody(String otp, String name) {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your OTP Code</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .email-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            overflow: hidden;
        }
        .email-header {
            background-color: #007bff;
            color: #ffffff;
            padding: 20px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
        }
        .email-body {
            padding: 20px;
            color: #333333;
        }
        .otp-code {
            font-size: 32px;
            font-weight: bold;
            color: #007bff;
            text-align: center;
            margin: 20px 0;
        }
        .email-footer {
            background-color: #f4f4f4;
            color: #666666;
            text-align: center;
            padding: 10px;
            font-size: 12px;
        }
        .email-footer a {
            color: #007bff;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="email-header">
            YOUR OTP CODE
        </div>
        <div class="email-body">
            <p>Hellow 
            """+name+"""
            ,</p>
            <p>We received a request to access your account. Use the code below to complete the verification process. If you did not request this, please ignore this email or contact support.</p>
            <div class="otp-code">
                """+ otp + """
                
                
            </div>
            <p>This code is valid for the next 10 minutes.</p>
            <p>Thank you,<br>The Job Portal Team</p>
        </div>
        <div class="email-footer">
            &copy; 2024 Job Portal | <a href="#">Privacy Policy</a> | <a href="#">Contact Support</a>
        </div>
    </div>
</body>
</html>
""";
    }
}
