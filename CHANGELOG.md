- Halplibe servers now maintain a list of vanilla clients connected to them.
  - Currently used to see if modded packets can be received by clients or if a compatibility packet should be used instead.
- Vanilla clients no longer get kicked from modded servers if anyone in the server dies.
  - The somewhat new DeathCause API did this by sending a modded packet to all (even vanilla) clients. 
  - A packet compatible with vanilla clients will be now sent instead.
  
Thanks to MelonMojito for this fix!