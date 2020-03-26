
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `apply` (
  `aid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `apply_uid` int(11) NOT NULL,
  `quote` float NOT NULL,
  `status` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'o',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `comment` (
  `cid` int(11) NOT NULL,
  `oid` int(11) NOT NULL,
  `from_uid` int(11) NOT NULL,
  `to_uid` int(11) NOT NULL,
  `ratings` int(11) NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `orders` (
  `oid` int(11) NOT NULL,
  `worker_uid` int(11) NOT NULL,
  `requester_uid` int(11) NOT NULL,
  `price` float NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `location` varchar(200) NOT NULL DEFAULT 'Not known',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'o',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `request` (
  `rid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `request_uid` int(11) NOT NULL,
  `price` float NOT NULL,
  `description` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `location` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'o',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `service` (
  `sid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` float NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `service` (`sid`, `uid`, `title`, `price`, `description`, `enabled`) VALUES
(1, 1, 'Regular Poker Game', 200, 'We have a poker game tournament every week. The entrance fees are $200 and must be paid before play begins. Leave your phone number and I\'ll send you the info.', 1),
(2, 2, 'Regular Baseketball Game', 0, 'There\'no entry fee. We usually play basketball around Millennium Park.', 0),
(3, 3, 'Car Detailing Service', 65, 'STANDARD MAINTENANCE SERVICE\r\nOverview:\r\n\r\nBoosts & Enhances Existing Wax/Sealant.\r\nKeeps Your Vehicle Looking Freshly Detailed.\r\nInterior Spruce Up.\r\nPrice:\r\n\r\nAll.........$65', 1),
(4, 4, 'Chinese Tutor', 9.99, 'I am a native Mandarin Chinese speaker from northern China where Mandarin Chinese is born. I graduated from a Chinese university. I also learned how to teach a second language from a graduate school in the US with a 3.9 GPA and scholarships. I have two years of experience teaching Mandarin Chinese as well.', 1),
(5, 5, 'English Online Tutor', 15.99, 'I enjoy traveling, going to the gym, meeting new people and learning about their culture. I am living in The United States and learning my second language so I understand how hard you are working and I can help with some of the struggles you might have. I really enjoy helping people learn english and would like to help you! We can speak or I can give you an english lesson. I hope we talk soon.', 1),
(6, 6, 'Python Coding Tutor', 29.99, 'I have started my career as a web developer 8 years ago. I like challenges in which I can use modern tools and technologies to develop outstanding web applications in look, performance and efficiency. I specialise in Front-End web technologies - Vue, React, and React Native for mobile applications. My Back-End stack includes Node.js with Express, Python, and PHP. For more details about technologies I know, see expertise section below.\r\n\r\nWhat I do in a nutshell\r\n\r\nSketching and Wireframe prototyping\r\nUX/UI Design\r\nFront-End/Back-End/Mobile\r\nTesting\r\nConsulting/Mentoring\r\nCode reviews\r\nWorkshops\r\nWhat I can help you with\r\n\r\nNew project or features\r\n\r\nDo you need a website or a mobile application? Or maybe you already have one, but you want to make it even better and include more features in it? I can build a website or a mobile application for you or help you with your current one. We can establish a short or long-term relationship, whichever suits your needs better. We can work on new features together via 1 to 1 sessions or it can be done via job requests.\r\n\r\nCode reviews/Consultancy\r\n\r\nDo you or your team have a project, but are not sure if you are following the best practices and patterns? Are you wondering how your code could be improved and be more efficient? Maybe you are about to start a new project but are not quite sure what technologies should you use or how to structure it for the best results? If that’s the case, then you came to the right person as I provide comprehensive code reviews with various suggestions on how you can improve your code and project. I can also advise you how you can get started.\r\n\r\nBug fixes and issue resolving\r\n\r\nBugs can be very annoying and hard to track and fix. Sometimes you can waste hours pulling your hair out while trying to find out what is wrong. I can help you resolve your issues quickly and efficiently, so you don’t waste any more time.\r\n\r\nTutoring/Teaching\r\n\r\nAre you new in the world of development or maybe you want to learn new technologies and tools to advance in your existing career? I offer regular mentoring session which are suited to meet your needs and time schedule. We can start from the basics or work on any specific aspect of programming that is of your interest.\r\n\r\nAnything else\r\n\r\nIf I did not mention something you need help with, we can still have a chat, so just drop me a message and we will see what can be done.', 1),
(7, 7, 'Dog Training', 20, 'WHY DO WE TRAIN?\r\nIt\'s both fun AND rewarding!\r\n\r\nSince we all hope to spend many years with our pets, dog training is an investment in both your future and your dog\'s future. Whether you\'re thinking about getting a new puppy, adopting an adult dog, or already own a dog and are looking to reach new training goals, we are here to help. Have fun with your dog while developing a stronger bond AND working towards peace of mind in your household - get started today!\r\n\r\n$20/hour', 1);


CREATE TABLE `task` (
  `tid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `location` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'o'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `task` (`tid`, `uid`, `title`, `location`, `description`, `create_time`, `status`) VALUES
(1, 1, 'House for sale', '4542 Heritage Road', 'Over an acre in the city limits, ALL one level for this price?? Whaaat? Awesome floor plan, master away from EVERYONE else! Can we get an AMEN? Things we love. It\'s built on a slab. What does that mean? No moisture issues! Eat in kitchen, AND dining room. Measuring in at 2066, it\'s the perfect size, not too big and not too little. JUST RIGHT! Wanting privacy? The back yard is perfect!', '2020-03-26 00:35:49', 'o'),
(2, 2, 'My car needs tire rotation ', '1084 Union Street\r\nSeattle, WA 98107', 'My car model is 19 Toyata RAV4. Give me a quote if you\'re around me.', '2020-03-26 00:34:32', 'o'),
(3, 3, '5000 Face masks needed', '3757 Shadowmar Drive\r\nMetairie, LA 70001', 'I\'m a businessman and need at least hundred-thousands face masks.\r\nGive me a quote if you have at least ten thousands face masks at this moment.', '2020-03-26 00:38:34', 'o'),
(4, 4, 'Car cleaning', '264 Johnson Street\r\nRaleigh, NC 27606', 'Well, I just came back from Alabama mudfest. My car\'s super duper dirty.', '2020-03-26 00:36:52', 'o'),
(5, 5, 'We need one poker player', '1543 Hall Street\r\nLas Vegas, NV 89119', 'We\'ll have a poker game around 10:00 p.m. The entry fee is $10. Leave how much chips you want to exchange.', '2020-03-26 00:40:29', 'o');

CREATE TABLE `token` (
  `id` int(11) NOT NULL,
  `access_token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `uid` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expired` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `pwd` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `fname` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `lname` varchar(100) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `user` (`uid`, `username`, `pwd`, `fname`, `lname`) VALUES
(1, 'admin', 'admin', 'Aite', 'Kuo'),
(2, 'admin2', 'admin2', 'Paul', 'Gonzalez'),
(3, 'admin3', 'admin3', 'Adrian', 'Soto'),
(4, 'admin4', 'admin4', 'John', 'Read'),
(5, 'admin5', 'admin5', 'Sandra', 'Wilken'),
(6, 'admin6', 'admin6', 'Vera', 'Lee'),
(7, 'admin7', 'admin7', 'Brenda', 'Jones');

ALTER TABLE `apply`
  ADD PRIMARY KEY (`aid`),
  ADD KEY `tid` (`tid`),
  ADD KEY `apply_uid` (`apply_uid`);

ALTER TABLE `comment`
  ADD PRIMARY KEY (`cid`),
  ADD KEY `oid` (`oid`),
  ADD KEY `from_uid` (`from_uid`),
  ADD KEY `to_uid` (`to_uid`);

ALTER TABLE `orders`
  ADD PRIMARY KEY (`oid`),
  ADD KEY `worker_uid` (`worker_uid`),
  ADD KEY `requester_uid` (`requester_uid`);

ALTER TABLE `request`
  ADD PRIMARY KEY (`rid`),
  ADD KEY `sid` (`sid`),
  ADD KEY `request_uid` (`request_uid`);

ALTER TABLE `service`
  ADD PRIMARY KEY (`sid`),
  ADD KEY `uid` (`uid`);

ALTER TABLE `task`
  ADD PRIMARY KEY (`tid`),
  ADD KEY `uid` (`uid`);

ALTER TABLE `token`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uid` (`uid`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`);

ALTER TABLE `apply`
  MODIFY `aid` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `comment`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `orders`
  MODIFY `oid` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `request`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `service`
  MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

ALTER TABLE `task`
  MODIFY `tid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

ALTER TABLE `apply`
  ADD CONSTRAINT `apply_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `task` (`tid`),
  ADD CONSTRAINT `apply_ibfk_2` FOREIGN KEY (`apply_uid`) REFERENCES `user` (`uid`);

ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`from_uid`) REFERENCES `user` (`uid`),
  ADD CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`to_uid`) REFERENCES `user` (`uid`);

ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`worker_uid`) REFERENCES `user` (`uid`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`requester_uid`) REFERENCES `user` (`uid`);

ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `service` (`sid`),
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`request_uid`) REFERENCES `user` (`uid`);

ALTER TABLE `service`
  ADD CONSTRAINT `service_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`);

ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`);

ALTER TABLE `token`
  ADD CONSTRAINT `token_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
