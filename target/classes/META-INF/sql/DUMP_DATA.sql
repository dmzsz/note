-- -----------------------------------------------------
-- DUMP USERS
-- -----------------------------------------------------

INSERT INTO user (id, login, password, email, name) VALUES
(1, 'admin', '$2a$10$MXqqS2vMDy9DAAB53udQ5Oc27OHoCXvoV.MtJZg7Lrg7kUDqxU/L.', 'admin@gmail.com', 'John');
INSERT INTO user (id, login, password, email, name) VALUES
(2, 'author', '$2a$10$RewEvCU1BRWPJPLwojKbZODmilxRuRDoVd5DmcqjT9QUrXfmgtefy', 'author@gmail.com', 'Peter');
INSERT INTO user (id, login, password, email, name) VALUES
(3, 'user', '$2a$10$7w5J60.3TQxGJZ0GGEKGEuU6bkDXWET1SGQZ1EfNE6/mdolLMDe0i', 'user@gmail.com', 'Ann');
(3, 'dmzsz', '$2a$10$Lfy.fsdQQc6eLM2AufOh.eaZocL9RjnV.dg6J6jtEVOnwrk2wCgrK', 'dmzsz@sina.com', 'dmzsz');

insert into role (id, authority)
values (1, 'ROLE_ADMIN');
insert into role (id, authority)
values (2, 'ROLE_USER');
insert into role (id, authority)
values (3, 'ROLE_AUTHOR');

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1);
INSERT INTO user_role (user_id, role_id) VALUES
(1, 2);
INSERT INTO user_role (user_id, role_id) VALUES
(1, 3);
INSERT INTO user_role (user_id, role_id) VALUES
(2, 2);
INSERT INTO user_role (user_id, role_id) VALUES
(2, 3);
INSERT INTO user_role (user_id, role_id) VALUES
(3, 3);
INSERT INTO user_role (user_id, role_id) VALUES
(4, 2);
INSERT INTO user_role (user_id, role_id) VALUES
(4, 3);
-- -----------------------------------------------------
-- DUMP CATEGORIES
-- -----------------------------------------------------

INSERT INTO categorie (id, name) VALUES
(1, 'Games');
INSERT INTO categorie (id, name) VALUES
(2, 'Movies');
INSERT INTO categorie (id, name) VALUES
(3, 'GameIndustry');
INSERT INTO categorie (id, name) VALUES
(4, 'Gadgets');

-- -----------------------------------------------------
-- DUMP ARTICLES
-- -----------------------------------------------------

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(1, 'Terminator: Genesis Starts Filming',
'Terminator: Genesis - the fifth film in the Terminator franchise - began principal photograph today in New Orleans.',
'<p><a href="http://www.ign.com/movies/terminator-5">Terminator: Genesis</a> - the fifth film in the Terminator franchise - began principal photograph today in New Orleans.</p>
<p>The film, directed by <a class="autolink" title="Alan Taylor" href="http://www.ign.com/stars/alan-taylor">Alan Taylor</a> (Thor: The Dark World, Game of Thrones), stars <a class="autolink" title="Emilia Clarke" href="http://www.ign.com/stars/emilia-clarke">Emilia Clarke</a> (Game of Thrones) as Sarah Connor, <a class="autolink" title="Jason Clarke" href="http://www.ign.com/stars/jason-clarke">Jason Clarke</a> (Zero Dark Thirty) as John Connor, and <a class="autolink" title="Jai Courtney" href="http://www.ign.com/stars/jai-courtney">Jai Courtney</a> (A Good Day to Die Hard) as Kyle Reese. More recent additions to the cast include Byng-hun Lee (G.I. Joe: Retaliation), <a class="autolink" title="J.K. Simmons" href="http://www.ign.com/stars/j-k-simmons">J.K. Simmons</a> (Spider-Man), Sandrine Holt (House of Cards) and Dayao Okeniyi (The Hunger Games).</p><p style="text-align: center"><strong><a href="http://www.ign.com/articles/2014/03/26/schwarzenegger-on-how-he-will-return-in-terminator-genesis">Schwarzenegger on How He Will Return in Terminator: Genesis</a></strong></p>
<p>Skydance Productions posted an image of a film slate (complete with an R.I.P. for late camera assistant Sarah Jones) on <a href="https://www.facebook.com/SkydanceProductions" rel="nofollow">their Facebook page</a> along with the message:</p>
<p>::SKYNET preparation routines complete.</p>
<p>::Initialization sequence complete.</p>
<p>::Reboot initiating in 03m00s.</p><p>Oh, and <a class="autolink" title="Arnold Schwarzenegger" href="http://www.ign.com/stars/arnold-schwarzenegger">Arnold Schwarzenegger</a> of course!</p>',
'1',
'2');

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(2, 'Nike Lays Off Staff, FuelBand Lines Future In Question',
'Nike is reportedly discontinuing its FuelBand line of wearable hardware, with up to 55 people laid off in the process, according to a confidential source.',
'<p><strong>UPDATE:</strong> Nike spokespeople <a href="http://recode.net/2014/04/18/nike-denies-fuelband-shutdown-but-layoffs-could-reveal-new-cracks-in-wearables-market/" target="_blank" rel="nofollow">speaking to Recode</a> have denied CNETs claims that it would discontinue the FuelBand hardware line, but confirmed that it has laid off a portion of its staff. In the very same report, however, the sites sources say the future of the line is still uncertain, with the company potentially clearing out FuelBand inventory before determining how to proceed with wearable tech.</p>
<p><!-- poilib start --></p>
<div class="accentDivider"></div>
<p><!-- poilib end --></p>
<p>Nike is reportedly discontinuing its FuelBand line of wearable hardware, with up to 55 people laid off in the process, according to a confidential source.</p>
<p>A person familiar with the matter disclosed the information <a href="http://www.cnet.com/news/nike-fires-fuelband-engineers-will-stop-making-wearable-hardware/" target="_blank" rel="nofollow">to CNET</a>, saying Nike plans on exiting the wearable-hardware market altogether. Apparently, with increasing competition expected from Google and Apple, the shoe company instead plans on focusing on athletic tracking software.</p>',
'1',
'4');

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(3, 'Minecraft to Aid Redesign Efforts Around the World',
'Plaza Tlaxcoaque in Mexico City is being eyed for a makeover, and the people responsible for a possible new design will present their ideas using models created in the popular PC game, Minecraft.',
'<p>Plaza Tlaxcoaque in Mexico City is being eyed for a makeover, and the people responsible for a possible new design will present their ideas using models created in the popular PC game, <a class="autolink" title="Minecraft" href="http://www.ign.com/games/minecraft/pc-92086">Minecraft</a>.</p>
<p>As <a href="http://mashable.com/2014/04/21/minecraft-united-nations/" target="_blank" rel="nofollow">Mashable</a> notes, the redesign effort is part of an initiative called <a href="http://blockbyblock.org/" target="_blank" rel="nofollow">Block by Block</a>. Community input is made possible by a partnership between the United Nations Human Settlements Programme (UN-Habitat) and Mojang, developers of the <a class="autolink" title="Minecraft" href="http://www.ign.com/games/minecraft/ps3-20005055">Minecraft</a> video game. The aim of the four-year program is to allow young people to design and upgrade a total of 300 public spaces by 2016.</p>
<p>The possible Plaza Tlaxcoaque makeover is one of the early projects. Three winners will present their designs, chosen from between 500 and 1000 estimated entries, on April 27, 2014. From there, the models will be turned over to architects to be turned into a real architectural design that can be presented to city officials.</p>
<p>Work on the pilot project in Nairobi is already underway, and similar efforts have begun in countries such as Haiti, India, and Nepal.</p>',
'2',
'1');

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(4, 'Danny Boyle Eyed to Direct Steve Jobs Biopic, Possibly Starring Leonardo DiCaprio',
'Sony Pictures is reportedly eying Oscar-winning director Danny Boyle (28 Days Later) to helm their Steve Jobs biopic, with Leonardo DiCaprio approached to star.',
'<p>Sony Pictures is reportedly eying Oscar-winning director <a class="autolink" title="Danny Boyle" href="http://www.ign.com/stars/danny-boyle">Danny Boyle</a> (28 Days Later) to helm their <a class="autolink" title="Steve Jobs" href="http://www.ign.com/stars/steve-jobs">Steve Jobs</a> biopic, with <a class="autolink" title="Leonardo DiCaprio" href="http://www.ign.com/stars/leonardo-dicaprio">Leonardo DiCaprio</a> approached to star.</p>
<p>According to <a href="http://www.hollywoodreporter.com/news/danny-boyle-talks-direct-steve-697992"target="_blank"  rel="nofollow">The Hollywood Reporter</a>, Boyle and DiCaprio are re-teaming for the film, based on the best-selling Jobs biography by Walter Isaacson. The duo previously worked together on 2000’s The Beach.</p>
<p>The film comes from a screenplay written by <a class="autolink" title="Aaron Sorkin" href="http://www.ign.com/stars/aaron-sorkin">Aaron Sorkin</a> (The Social Network). Past Sorkin collaborator <a class="autolink" title="David Fincher" href="http://www.ign.com/stars/david-fincher">David Fincher</a> was originally approached to direct, before backing out “due to his aggressive demands for compensation and control,” says THR. Fincher had reportedly wanted Christian Bale for the starring role.</p>
<p>Surrounding the life of the late Apple co-founder, the film marks the first time Sorkin has penned a script for Boyle and DiCaprio.</p>
<p>The Jobs film doesn’t yet have a release.</p>',
'2',
'2');

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(5, 'Space Case: Galactic Civilizations III is a 4X Work in Progress',
'When you launch into the Galactic Civilizations III alpha, you get a disclaimer imploring you to “please be aware that the game isnt actually, well, fun yet.”',
'<p>When you launch into the <a class="autolink" title="Galactic Civilizations III" href="http://www.ign.com/games/galactic-civilizations-iii/pc-14277477">Galactic Civilizations III</a> alpha, you get a disclaimer imploring you to “please be aware that the game isnt actually, well, fun yet.” Stardock isnt kidding with this statement: GalCiv III is truly in alpha state right now, and its more a preview of coming attractions than any kind of functional strategy game. Anyone considering paying for Early Access to this alpha should set aside any illusions that they will actually be playing the game. GalCiv III will eventually be a 4X space strategy game. Its alpha is a 3X, at best.</p>
<p>The current build of the game lacks three huge features that havent been implemented yet, and without them, its hard to say just how well GalCiv III will end up coming together. First, the AI is non-responsive, and there is no diplomacy system to foster interaction anyway. They will occasionally build ships (more by happenstance than design) and theyre quite eager to colonize every planet under every sun. But theres nothing you can do with them except conquer them, and that will prove very, very easy.</p>',
'1',
'1');

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(6, 'The Elder Scrolls Online: Review in Progress',
'Reviewing an MMORPG is a big job that can take weeks to complete. Our final score will be awarded at that time, once weve put in enough time to get a sense of The Elder Scrolls Onlines true strengths and weaknesses.',
'<p><strong>April 9: End-Game Bugs, Veteran Content, and the Economy</strong></p>
<p dir="ltr">I hit the level cap of 50 <a href="https://twitter.com/LeifJohnson/status/453410927430234112" rel="nofollow">early yesterday morning</a>, and Id intended to spend today talking about the "Veteran" content in Elder Scrolls Online – i.e., what currently passes for the end game. (The <a href="http://www.ign.com/articles/2014/04/07/the-elder-scrolls-online-first-content-patch-revealed">recent news about Craglorn</a> hints at better things to come.) Alas, for two day entire days now, the "Groundskeeper" NPC that unlocks so much of the content in the final zone of <a href="http://www.ign.com/wikis/elder-scrolls-online/Coldharbour"title="Coldharbour" >Coldharbour</a> has been bugged for the <a href="http://www.ign.com/wikis/elder-scrolls-online/Ebonheart_Pact"title="Ebonheart Pact" >Ebonheart Pact</a>, restricting me and seemingly hundreds of other players from experiencing Veteran content outside of Cyrodiil PvP and Veteran dungeons. So much for that.</p>
<p>Its even worse for lower-level characters, as the same NPC is bugged for them as well. At the heart of Coldharbour stands a town known as the Hollow City, and you fill it with NPCs much as you fill Mass Effects Normandy by completing quests. Since the Groundskeeper is bugged, thus keeping all the content she unlocks inaccessible, an unfortunate number of players have been led to believe that the final zone consists of nothing but grinding through <a href="http://www.ign.com/wikis/elder-scrolls-online/Enemies"title="enemies" >enemies</a>. I fortunately managed to complete the bulk of the Coldharbour quests before I ran into this problem, but for  now the final chapter in the main story isnt available.</p>
<p>Im especially amused that its a quintessential <a href="http://www.ign.com/wikis/elder-scrolls-online/Bugs_and_Glitches"title="Elder Scrolls bug" >Elder Scrolls bug</a>. Much like Karliah in unpatched versions of the end of Skyrims Thieves Guild quest, the Groundskeeper just sits there, a quest marker over her head, but she never responds no matter how often you log in or out or wait. Considering how key the NPC is, its inexcusable that she hasnt been fixed yet. Much as I said in my first review in progress, these bugs arent terribly common (or theyre usually repairable by logging out and logging back in), but the whoppers always seem to affect the most essential NPCs.</p>
<p>But anyway. Veteran levels are somewhat like Paragon levels in Diablo III, as you earn them after youve completed the main leveling content. The difference is that <a href="http://www.ign.com/wikis/elder-scrolls-online/Armor"title="Veteran gear" >Veteran gear</a> also has Veteran level requirements much as the standard gear. Allegedly – remember, I havent seen it – Veteran zones let you access the content of the two other factions, complete with high-level profession materials and additional skill points, thus verifying ZeniMaxs earlier claims that reaching the level cap consists of a mere one-third of the PvE content.</p>',
'2',
'1');

INSERT INTO article (id, title, preview, content, user_id, category_id) VALUES
(7, 'Microsoft Files Eden Falls Trademark',
'With less than two months until E3, the trademark applications continue piling up for Microsoft, which filed a listing for "Eden Falls" with the U.S. Patent and Trademark Office on April 16.',
'<p>With less than two months until E3, the trademark applications continue piling up for <a class="autolink" title="Microsoft" href="http://www.ign.com/companies/microsoft">Microsoft</a>, which filed a listing for "Eden Falls" with the U.S. Patent and Trademark Office on April 16.</p>
<p>Spotted <a href="http://www.neogaf.com/forum/showthread.php?t=805299" target="_blank" rel="nofollow">on NeoGAF</a>, Microsofts <a href="http://tsdr.uspto.gov/#caseNumber=86253360\&amp;caseType=SERIAL_NO&\amp;searchType=statusSearch" target="_blank" rel="nofollow">application for</a> "Eden Falls" doesnt reveal too much information, other than the fact that the trademark is for "entertainment services, namely, providing an online computer game." But there <a href="http://www.edenfallsgame.com/blog/" target="_blank" rel="nofollow">is a blog </a>for an Eden Falls game, purportedly a sci-fi RPG made by Brimstone Interactive. Contradicting that information, though, is an Eden Falls <a href="https://twitter.com/edenfallsgame" target="_blank" rel="nofollow">Twitter account</a> that lists <a href="http://www.liftlondon.com" target="_blank" rel="nofollow">Lift London</a> as the developer, a Microsoft studio known for making games on tablets and smartphones.</p>
<p>IGN has reached out to Microsoft for a comment regarding the "Eden Falls" trademark filing.</p>',
'2',
'3');

-- -----------------------------------------------------
-- DUMP TAGS
-- -----------------------------------------------------

INSERT INTO tag (id,name) VALUES
(1, 'Terminator');
INSERT INTO tag (id,name) VALUES
(2, 'Schwarzenegger');
INSERT INTO tag (id,name) VALUES
(3, 'Nike');
INSERT INTO tag (id,name) VALUES
(4, 'FuelBand');
INSERT INTO tag (id,name) VALUES
(5, 'Minecraft');
INSERT INTO tag (id,name) VALUES
(6, 'PC');
INSERT INTO tag (id,name) VALUES
(7, 'Danny Boyle');
INSERT INTO tag (id,name) VALUES
(8, 'DiCaprio');
INSERT INTO tag (id,name) VALUES
(9, 'Steve Jobs');
INSERT INTO tag (id,name) VALUES
(10, 'GalCiv');
INSERT INTO tag (id,name) VALUES
(11, 'MMORPG');
INSERT INTO tag (id,name) VALUES
(12, 'TES');
INSERT INTO tag (id,name) VALUES
(13, 'Online');
INSERT INTO tag (id,name) VALUES
(14, 'Microsoft'); 
INSERT INTO tag (id,name) VALUES
(15,'E3');
INSERT INTO tag (id,name) VALUES
(16, 'Game');

INSERT INTO article_tag (article_id, tag_id) VALUES
(1, 1);
INSERT INTO article_tag (article_id, tag_id) VALUES
(1, 2);
INSERT INTO article_tag (article_id, tag_id) VALUES
(2, 3);
INSERT INTO article_tag (article_id, tag_id) VALUES
(2, 4);
INSERT INTO article_tag (article_id, tag_id) VALUES
(3, 5);
INSERT INTO article_tag (article_id, tag_id) VALUES
(3, 6);
INSERT INTO article_tag (article_id, tag_id) VALUES
(3, 16);
INSERT INTO article_tag (article_id, tag_id) VALUES
(4, 7);
INSERT INTO article_tag (article_id, tag_id) VALUES
(4, 8);
INSERT INTO article_tag (article_id, tag_id) VALUES
(4, 9);
INSERT INTO article_tag (article_id, tag_id) VALUES
(5, 10);
INSERT INTO article_tag (article_id, tag_id) VALUES
(5, 6);
INSERT INTO article_tag (article_id, tag_id) VALUES
(5, 16);
INSERT INTO article_tag (article_id, tag_id) VALUES
(6, 11);
INSERT INTO article_tag (article_id, tag_id) VALUES
(5, 12);
INSERT INTO article_tag (article_id, tag_id) VALUES
(6, 3);
INSERT INTO article_tag (article_id, tag_id) VALUES
(6, 6);
INSERT INTO article_tag (article_id, tag_id) VALUES
(6, 16),
INSERT INTO article_tag (article_id, tag_id) VALUES
(7, 14);
INSERT INTO article_tag (article_id, tag_id) VALUES
(7, 15);